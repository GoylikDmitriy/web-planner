import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { DatePicker } from 'antd';
import Tasks from "./Tasks";
import Plans from "./Plans";

export default function Planner() {
    const [employees, setEmployees] = useState([]);
    const [selectedEmployeeId, setSelectedEmployeeId] = useState(null);
    const [selectedStartDate, setSelectedStartDate] = useState();
    const [selectedEndDate, setSelectedEndDate] = useState();
    const [tasks, setTasks] = useState([]);

    const [error, setError] = useState('');

    useEffect(() => {
        axios.get('/planner/employees')
            .then((res) => setEmployees(res.data))
            .catch((error) => console.log(error));
    }, []);

    const handleEmployeeChange = (event) => {
        const employeeId = event.target.value;
        setSelectedEmployeeId(employeeId);
    };

    const handleStartDateChange = (date, dateString) => {
        setSelectedStartDate(dateString);
    };

    const handleEndDateChange = (date, dateString) => {
        setSelectedEndDate(dateString);
    };

    const showTasksClick = () => {
        if (selectedEmployeeId && selectedEmployeeId >= 0) {
            if (selectedStartDate && selectedEndDate) {
                const startDate = formatDate(selectedStartDate);
                const endDate = formatDate(selectedEndDate);

                axios.get(`/planner/tasks?employeeId=${selectedEmployeeId}&startDate=${startDate}&endDate=${endDate}`)
                    .then(res => setTasks(res.data))
                    .catch(error => console.log(error));

                setError('');
            }
            else {
                setError('Выберите дату.');
            }
        }
        else {
            setError('Выберите сотрудника.');
        }
    }

    function formatDate(dateString) {
        const [day, month, year] = dateString.split('.');
        return `${year}-${month}-${day}`;
    }

    return (
        <div>
            <div className={'container border rounded p-3 m-3 mt-3 w-25'}>
                <div className={'input-group'}>
                    <span className={'input-group-text'}>Сотрудник</span>
                    <select className={'form-select'} onChange={handleEmployeeChange}>
                        <option key={-1} value={-1}>Выберите сотрудника</option>
                        {employees.map((e) => (
                            <option key={e.id} value={e.id}>{e.last_name}</option>
                        ))}
                    </select>
                </div>
                <div className={'input-group pt-3 d-flex flex-nowrap'}>
                    <span className={'input-group-text'}>с</span>
                    <DatePicker className={'from-control'}
                                format={'DD.MM.YYYY'}
                                onChange={handleStartDateChange}
                    />
                    <span className={'input-group-text'}>по</span>
                    <DatePicker className={'from-control'}
                                format={'DD.MM.YYYY'}
                                onChange={handleEndDateChange}
                    />
                </div>
                <div className={'pt-3'}>
                    <div className={'d-flex justify-content-start'}>
                        <span className={'text-danger'}>{error}</span>
                    </div>
                    <div className={'d-flex justify-content-end'}>
                        <button className={'btn btn-primary'} onClick={showTasksClick}>Показать задачи</button>
                    </div>
                </div>
            </div>
            <div className={'container p-3 m-3'}>
                <ul className={'nav nav-tabs'}>
                    <li className={'nav-item'}>
                        <button className={'nav-link active'} data-bs-toggle='tab' data-bs-target='#tasks-tab'>Задачи</button>
                    </li>
                    <li className={'nav-item'}>
                        <button className={'nav-link'} data-bs-toggle='tab' data-bs-target='#plan-tab'>План</button>
                    </li>
                </ul>
                <div className={'tab-content'}>
                    <Tasks tab_id={'tasks-tab'} tasks={tasks}/>
                    <Plans tab_id={'plan-tab'} tasks={tasks} startDate={selectedStartDate} endDate={selectedEndDate}
                           formatDate={formatDate}/>
                </div>
            </div>
        </div>
    );
}