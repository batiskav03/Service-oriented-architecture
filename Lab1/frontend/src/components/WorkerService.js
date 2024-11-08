import React, { useState, useRef } from 'react';
import '98.css';

const WorkerService = () => {
    const [workerId, setWorkerId] = useState(''); // ID работника
    const [worker, setWorker] = useState(null); // Данные о работнике
    const [errorMessage, setErrorMessage] = useState(''); // Сообщение об ошибке
    const [isLoading, setIsLoading] = useState(false); // Статус загрузки
    const [isModalOpen, setIsModalOpen] = useState(false); // Состояние для модального окна
    const [mode, setMode] = useState(null); // Выбор режима (получить информацию или удалить)
    const [successMessage, setSuccessMessage] = useState(''); // Сообщение об успешном удалении работника

    // Состояния для перетаскивания иконки
    const [iconPosition, setIconPosition] = useState({ x: 20, y: 20 });
    const [dragging, setDragging] = useState(false);
    const iconRef = useRef(null); // Ссылка на иконку
    const windowRef = useRef(null); // Ссылка на окно

    // Перетаскивание иконки
    const startDrag = (e) => {
        setDragging(true);
        e.preventDefault();
    };

    const onDrag = (e) => {
        if (dragging) {
            setIconPosition({
                x: e.clientX - iconRef.current.clientWidth / 2,
                y: e.clientY - iconRef.current.clientHeight / 2,
            });
        }
    };

    const stopDrag = () => {
        setDragging(false);
    };

    // Функция для обработки запроса на получение информации о работнике
    const handleGetWorker = async () => {
        if (!workerId) {
            setErrorMessage('Пожалуйста, введите ID работника.');
            return;
        }

        setIsLoading(true);
        setErrorMessage(''); // Очищаем старое сообщение об ошибке

        try {
            const response = await fetch(`http://localhost:8080/api/worker/get/${workerId}`);

            if (!response.ok) {
                const result = await response.json();
                setErrorMessage(result.message || 'Что-то пошло не так.');
                setWorker(null);
            } else {
                const result = await response.json();
                setWorker(result.worker);
                setErrorMessage('');
            }
        } catch (error) {
            setErrorMessage('Ошибка соединения: ' + error.message);
            setWorker(null);
        }

        setIsLoading(false);
    };

    // Функция для обработки удаления работника
    const handleDeleteWorker = async () => {
        if (!workerId) {
            setErrorMessage('Пожалуйста, введите ID работника для удаления.');
            return;
        }

        setIsLoading(true);
        setErrorMessage(''); // Очищаем старое сообщение об ошибке
        setSuccessMessage(''); // Очищаем старое сообщение об успешном удалении

        try {
            const response = await fetch(`http://localhost:8080/api/worker/delete/${workerId}`, {
                method: 'DELETE',
            });

            if (!response.ok) {
                const result = await response.json();
                setErrorMessage(result.message || 'Не удалось удалить работника.');
            } else {
                const result = await response.json();
                setErrorMessage('');
                setWorker(null);
                setSuccessMessage(`Работник с ID ${workerId} был удален.`);
            }
        } catch (error) {
            setErrorMessage('Ошибка соединения: ' + error.message);
        }

        setIsLoading(false);
    };

    // Открытие и закрытие модального окна
    const openModal = () => setIsModalOpen(true);
    const closeModal = () => setIsModalOpen(false);

    // Закрытие окна с информацией о работнике
    const closeWorkerInfoWindow = () => setMode(null);

    return (
        <div style={{ backgroundImage: 'url(/Win98-Desk.png)', backgroundSize: 'cover', height: '100vh' }} onMouseMove={onDrag}>

            {/* Иконка с перетаскиванием */}
            <div
                ref={iconRef}
                onClick={openModal}
                className="app-icon"
                style={{
                    position: 'absolute',
                    top: iconPosition.y + 'px',
                    left: iconPosition.x + 'px',
                    cursor: 'pointer',
                    textAlign: 'center',
                    fontFamily: 'Arial',
                    fontSize: '16px',
                    color: 'white',
                }}
                onMouseDown={startDrag}
                onMouseUp={stopDrag}
            >
                <img
                    src="/icon.png"
                    alt="App Icon"
                    style={{
                        width: '32px',
                        height: '32px',
                        border: '1px solid #000',
                        backgroundColor: 'transparent', // Убираем фон
                        borderRadius: '4px',
                        padding: '4px',
                        boxShadow: '2px 2px 5px rgba(0, 0, 0, 0.5)',
                    }}
                />
                <div>Worker Service</div>
            </div>

            {/* Модальное окно для выбора режима */}
            {isModalOpen && (
                <div
                    className="window"
                    style={{
                        position: 'absolute',
                        top: '120px',
                        left: '20px',
                        maxWidth: '400px',
                        padding: '10px',
                        zIndex: 10,
                        background: 'white',
                        borderRadius: '5px',
                    }}
                >
                    <div className="title-bar">
                        <div className="title-bar-text">Выберите режим</div>
                        <div className="title-bar-controls">
                            <button aria-label="Close" onClick={closeModal}></button>
                        </div>
                    </div>
                    <div className="window-body">
                        <button
                            onClick={() => {
                                setMode('getWorker'); // Устанавливаем режим получения информации
                                closeModal(); // Закрываем окно выбора режима
                            }}
                            className="button"
                            style={{ width: '100%', marginBottom: '10px' }}
                        >
                            Получить информацию о работнике
                        </button>
                        <button
                            onClick={() => {
                                setMode('deleteWorker'); // Устанавливаем режим удаления работника
                                closeModal(); // Закрываем окно выбора режима
                            }}
                            className="button"
                            style={{ width: '100%', marginBottom: '10px' }}
                        >
                            Удалить работника
                        </button>
                    </div>
                </div>
            )}

            {/* Окно с информацией о работнике */}
            {mode === 'getWorker' && (
                <div
                    className="window"
                    style={{
                        position: 'absolute',
                        top: '180px',
                        left: '20px',
                        maxWidth: '400px',
                        padding: '10px',
                        zIndex: 10,
                        background: 'white',
                        borderRadius: '5px',
                    }}
                >
                    <div className="title-bar">
                        <div className="title-bar-text">Worker Information Service</div>
                        <div className="title-bar-controls">
                            <button aria-label="Close" onClick={closeWorkerInfoWindow}></button>
                        </div>
                    </div>
                    <div className="window-body">
                        <div>
                            <label>Введите ID работника:</label>
                            <input
                                type="text"
                                value={workerId}
                                onChange={(e) => setWorkerId(e.target.value)}
                                className="input"
                                placeholder="Введите ID"
                                style={{ width: '100%', marginBottom: '10px' }}
                            />
                        </div>
                        <button
                            onClick={handleGetWorker}
                            className="button"
                            style={{ width: '100%', marginBottom: '10px' }}
                        >
                            Получить информацию
                        </button>

                        {isLoading && <p>Загрузка...</p>}

                        {errorMessage && (
                            <div style={{ color: 'red', marginTop: '10px' }}>
                                <p>{errorMessage}</p>
                            </div>
                        )}

                        {worker && !errorMessage && (
                            <div style={{ marginTop: '20px' }}>
                                <h3>Информация о работнике:</h3>
                                <p><strong>ID:</strong> {worker.id}</p>
                                <p><strong>Имя:</strong> {worker.name}</p>
                                <p><strong>Должность:</strong> {worker.position}</p>
                                <p><strong>Дата начала работы:</strong> {worker.startDate}</p>
                            </div>
                        )}
                    </div>
                </div>
            )}

            {/* Окно для удаления работника */}
            {mode === 'deleteWorker' && (
                <div
                    className="window"
                    style={{
                        position: 'absolute',
                        top: '180px',
                        left: '20px',
                        maxWidth: '400px',
                        padding: '10px',
                        zIndex: 10,
                        background: 'white',
                        borderRadius: '5px',
                    }}
                >
                    <div className="title-bar">
                        <div className="title-bar-text">Удалить работника</div>
                        <div className="title-bar-controls">
                            <button aria-label="Close" onClick={closeWorkerInfoWindow}></button>
                        </div>
                    </div>
                    <div className="window-body">
                        <div>
                            <label>Введите ID работника для удаления:</label>
                            <input
                                type="text"
                                value={workerId}
                                onChange={(e) => setWorkerId(e.target.value)}
                                className="input"
                                placeholder="Введите ID"
                                style={{ width: '100%', marginBottom: '10px' }}
                            />
                        </div>
                        <button
                            onClick={handleDeleteWorker}
                            className="button"
                            style={{ width: '100%', marginBottom: '10px' }}
                        >
                            Удалить работника
                        </button>

                        {isLoading && <p>Загрузка...</p>}

                        {errorMessage && (
                            <div style={{ color: 'red', marginTop: '10px' }}>
                                <p>{errorMessage}</p>
                            </div>
                        )}

                        {successMessage && (
                            <div style={{ color: 'green', marginTop: '10px' }}>
                                <p>{successMessage}</p>
                            </div>
                        )}
                    </div>
                </div>
            )}
        </div>
    );
};

export default WorkerService;
