import React from "react";

export default function Plans({tab_id, tasks, startDate, endDate, formatDate}) {

    function getDates(startDate, endDate) {
        const dates = [];

        if (startDate && endDate) {
            const start = new Date(formatDate(startDate));
            const end = new Date(formatDate(endDate));

            let currentDate = start;
            while (currentDate <= end) {
                dates.push(new Date(currentDate));
                currentDate.setDate(currentDate.getDate() + 1);
            }
        }

        return dates;
    }

    function isDateInRange(date, startDate, endDate) {
        return date >= new Date(formatDate(startDate)) && date <= new Date(formatDate(endDate));
    }

    return (
        <div className={'tab-pane fade table-responsive'} id={tab_id}>
            <table className={'table table-bordered'}>
                <thead>
                <tr>
                    <th>Задача</th>
                    {getDates(startDate, endDate).map((d, i) => (
                        <th key={i} className={`text-center ${i % 2 === 0 ? 'table-light' : ''}`}>
                            {String(d.getDate()).padStart(2, '0')}
                        </th>
                    ))}
                </tr>
                </thead>
                <tbody>
                {tasks.map(t => (
                    <tr key={t.id}>
                        <td>{t.task}</td>
                        {getDates(startDate, endDate).map((d, i) => (
                            <td key={i} className={`${isDateInRange(d, t.start_date, t.end_date) ? 'table-info' :
                                i % 2 === 0 ? 'table-light' : ''}`}>
                            </td>
                        ))}
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}