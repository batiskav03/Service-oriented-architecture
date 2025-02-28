package com.soa.ejb;

import com.soa.ejb.models.*;
import lombok.extern.slf4j.Slf4j;
import org.jboss.ejb3.annotation.Pool;


import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Stateless
@Remote(com.soa.ejb.WorkerRemote.class)
@Pool("products-pool")
public class WorkerBean implements WorkerRemote {

    @Resource(lookup = "java:/workersDS")
    private DataSource dataSource;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("BaseDataSource is unable to load org.postgresql.Driver. Please check if you have proper PostgreSQL JDBC Driver jar on the classpath", e);
        }
    }



    @Override
    public Worker getWorker(UUID id) {
        Worker worker = this.getWorkerById(id);

        if (worker == null){ return null;}

        return worker;
    }

    @Override
    public Integer deleteWorker(UUID id) {
        Worker worker = this.getWorker(id);
        if (worker == null) {
            log.error("Worker {} not found", id);
            return 404;
        }
        this.deleteWorkerById(id);
        return 200;
    }

    @Override
    public Worker updateWorkerField(UUID id, String field, Object value) {
        Worker worker = this.getWorkerById(id);
        if (worker != null) {
            switch (field) {
                case "name":
                    worker.setName((String) value);
                    break;
                case "position":
                    worker.setPosition((Position) value);
                    break;
                case "salary":
                    worker.setSalary((Long) value);
                    break;
                default:
                    log.error("Invalid field {}", field);
                    return null;
            }
            this.save(worker);
            return worker;
        }
        log.error("Worker {} not found", id);
        return null;
    }

    @Override
    public Integer createWorker(Worker worker) {
        this.save(worker);

        if (this.getWorker(worker.getId()) != null) {
            return 200;
        }
        log.error("Worker {} not found", worker.getId());
        return 404;
    }

    @Override
    public List<Worker> getAllWorkers(String sorting, SearchCriteria filter, int page, int pageSize) {
        return this.findAll();
    }

    @Override
    public List<Worker> selectUniqWorkerPositions() {
        return this.findAll().stream()
                               .map(Worker::getPosition)
                               .distinct()
                               .map(position -> new Worker())
                               .toList();
    }


    private static final String URL = "jdbc:postgresql://worker-postgres:5432/worker_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void executeUpdate(String sql, Object... params) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            stmt.executeUpdate();
        }
    }

    public ResultSet executeQuery(String sql, Object... params) throws SQLException {
        Connection conn = getConnection();

        PreparedStatement stmt = conn.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
        return stmt.executeQuery();
    }

    public Worker getWorkerById(UUID id) {
        try {
            ResultSet rs = this.executeQuery("SELECT * FROM worker where id = ?", id);
            Worker worker = new Worker();
            rs.next();
            worker.setId(rs.getObject("id", UUID.class));
            worker.setName(rs.getString("name"));
            worker.setSalary(rs.getLong("salary"));
            System.out.println(worker);
            return worker;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteWorkerById(UUID id) {
        try {
            this.executeUpdate("DELETE FROM worker where id = ?", id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Worker> findAll() {
        try {
            ResultSet rs = this.executeQuery("select worker.id, worker.name, co.id as co_id, co.x, co.y, " +
                    "worker.creation_date, worker.salary, worker.start_date, " +
                    "worker.status, worker.position, " +
                    "p.id as p_id, p.birthday, p.passport_uuid, p.haircolor, p.nationality " +
                    "from worker " +
                    "left join coordinates co on worker.coordinates_id = co.id " +
                    "left join person p on worker.person_id = p.id");
            List<Worker> workers = new ArrayList<>();
            while (rs.next()) {
                workers.add(new Worker(
                        rs.getObject("id", UUID.class),
                        rs.getString("name"),
                        new Coordinates(rs.getObject("co_id", UUID.class), rs.getInt("x"), rs.getInt("y")),
                        rs.getDate("creation_date"),
                        rs.getLong("salary"),
                        rs.getDate("start_date"),
                        rs.getString("status") != null ? Status.valueOf(rs.getString("status")) : null,
                        rs.getString("position") != null ? Position.valueOf(rs.getString("position")) : null,
                        new Person(rs.getObject("p_id", UUID.class),
                                rs.getDate("birthday"),
                                rs.getObject("passport_uuid", UUID.class),
                                rs.getString("haircolor") != null ? HairColor.valueOf(rs.getString("haircolor")) : null,
                                rs.getString("nationality") != null ? Nationality.valueOf(rs.getString("nationality")) : null
                        )
                ));
            }

            return workers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public void save(Worker worker) {
        worker.setId(UUID.randomUUID());
        try {
            this.executeUpdate(
                    "INSERT INTO worker (id, name, coordinates_id," +
                            " creation_date, salary, start_date," +
                            " status, position, person_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    worker.getId(), worker.getName(), null, worker.getCreationDate(),
                    worker.getSalary(), null, worker.getStatus(), worker.getPosition().toString(), null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}