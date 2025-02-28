import React, { useState, useRef } from 'react';
import '98.css';

const Modal = ({ isOpen, onClose, onFire, onHire }) => {
    const [isHiring, setIsHiring] = useState(false);
    const [fireId, setFireId] = useState('');
    const [personId, setPersonId] = useState('');
    const [position, setPosition] = useState('');
    const [startDate, setStartDate] = useState('');

    if (!isOpen) return null;

    const handleFireClick = () => {
        onFire(fireId);
    };

    const handleHireClick = () => {
        onHire(personId, position, startDate);
    };

    return (
        <div className="modal-overlay">
            <div className="window" style={{ width: '300px' }}>
                <div className="title-bar">
                    <div className="title-bar-text">Выберите действие</div>
                    <div className="title-bar-controls">
                        <button aria-label="Close" onClick={onClose}></button>
                    </div>
                </div>
                <div className="window-body">
                    <p>Выберите режим:</p>
                    <div className="button-group">
                        <button onClick={() => setIsHiring(false)} className="button">
                            Уволить
                        </button>
                        <button onClick={() => setIsHiring(true)} className="button">
                            Нанять
                        </button>
                    </div>

                    {!isHiring ? (
                        <div className="field-row-stacked" style={{ marginTop: '10px' }}>
                            <label>Введите ID для увольнения:</label>
                            <input
                                type="text"
                                value={fireId}
                                onChange={(e) => setFireId(e.target.value)}
                                className="input"
                                placeholder="Введите ID"
                            />
                            <button onClick={handleFireClick} className="button" style={{ marginTop: '10px' }}>
                                Подтвердить увольнение
                            </button>
                        </div>
                    ) : (
                        <div style={{ marginTop: '10px' }}>
                            <div className="field-row-stacked">
                                <label>Введите ID сотрудника:</label>
                                <input
                                    type="text"
                                    value={personId}
                                    onChange={(e) => setPersonId(e.target.value)}
                                    className="input"
                                    placeholder="Введите person-id"
                                />
                            </div>
                            <div className="field-row-stacked">
                                <label>Введите позицию:</label>
                                <input
                                    type="text"
                                    value={position}
                                    onChange={(e) => setPosition(e.target.value)}
                                    className="input"
                                    placeholder="Введите позицию"
                                />
                            </div>
                            <div className="field-row-stacked">
                                <label>Введите дату начала:</label>
                                <input
                                    type="text"
                                    value={startDate}
                                    onChange={(e) => setStartDate(e.target.value)}
                                    className="input"
                                    placeholder="Введите дату начала (YYYY-MM-DD)"
                                />
                            </div>
                            <button onClick={handleHireClick} className="button" style={{ marginTop: '10px' }}>
                                Подтвердить найм
                            </button>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

const HrServiceWindow = () => {
    const SERVICE_URL = "http://localhost:8083/"
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [serverResponse, setServerResponse] = useState(null);


    const [isDragging, setIsDragging] = useState(false);
    const [dragOffset, setDragOffset] = useState({ x: 0, y: 0 });
    const iconRef = useRef(null);

    const openModal = () => setIsModalOpen(true);
    const closeModal = () => setIsModalOpen(false);

    const handleMouseDown = (e) => {
        setIsDragging(true);
        const iconBounds = iconRef.current.getBoundingClientRect();
        setDragOffset({
            x: e.clientX - iconBounds.left,
            y: e.clientY - iconBounds.top,
        });
    };

    const handleMouseMove = (e) => {
        if (!isDragging) return;
        const newX = e.clientX - dragOffset.x;
        const newY = e.clientY - dragOffset.y;
        iconRef.current.style.left = `${newX}px`;
        iconRef.current.style.top = `${newY}px`;
    };

    const handleMouseUp = () => {
        setIsDragging(false);
    };

    const handleFire = async (fireId) => {
        try {
            const response = await fetch(`${SERVICE_URL}/hr/fire/${fireId}`, {
                method: 'DELETE',
            });

            if (response.ok) {
                const result = await response.text();
                setServerResponse(`Успех: ${result}`);
            } else {
                const errorText = await response.text();
                setServerResponse(`Ошибка: ${errorText}`);
            }
        } catch (error) {
            setServerResponse(`Ошибка соединения: ${error.message}`);
        }
        closeModal();
    };

    const handleHire = async (personId, position, startDate) => {
        try {
            const response = await fetch(
                `${SERVICE_URL}/hr/hire/${personId}/${position}/${startDate}`,
                {
                    method: 'POST',
                }
            );

            if (response.ok) {
                const result = await response.text();
                setServerResponse(`Успех: ${result}`);
            } else {
                const errorText = await response.text();
                setServerResponse(`Ошибка: ${errorText}`);
            }
        } catch (error) {
            setServerResponse(`Ошибка соединения: ${error.message}`);
        }
        closeModal();
    };

    return (
        <div
            style={{
                backgroundImage: 'url(/Win98-Desk.png)',
                backgroundSize: 'cover',
                height: '100vh',
                padding: '20px',
            }}
            onMouseMove={handleMouseMove}
            onMouseUp={handleMouseUp}
            onMouseLeave={handleMouseUp}
        >
            <div
                ref={iconRef}
                onMouseDown={handleMouseDown}
                onClick={openModal}
                className="app-icon"
                style={{
                    cursor: 'move',
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                    width: '60px',
                    height: '60px',
                    backgroundImage: 'url(/icon2.png)',
                    backgroundSize: 'contain',
                    backgroundRepeat: 'no-repeat',
                    borderRadius: '10px',
                    margin: '20px',
                    position: 'absolute',
                    textAlign: 'center',
                    color: 'white',
                    fontWeight: 'bold',
                    mixBlendMode: '',

                }}
                title="Выбрать действие"
            >
                <div
                    style={{
                        textAlign: 'center',
                        marginTop: '50px',
                        fontFamily: 'Microsoft Sans Serif',
                        fontSmooth: 'none',
                        color: 'white',
                        fontSize: '15px',
                        fontWeight: 'lighter'
                    }}
                >
                    HR Service
                </div>
            </div>
            <Modal
                isOpen={isModalOpen}
                onClose={closeModal}
                onFire={handleFire}
                onHire={handleHire}
            />
            {serverResponse && (
                <div className="window response-window" style={{ marginTop: '10px', padding: '10px' }}>
                    <div className="window-body">
                        <p>{serverResponse}</p>
                    </div>
                </div>
            )}
        </div>
    );
};

export default HrServiceWindow;
