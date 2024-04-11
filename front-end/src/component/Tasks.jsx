import React from "react";

export default function Tasks({tab_id, tasks}) {
    return (
        <div className={'tab-pane fade show active'} id={tab_id}>
            <table className={'table table-striped'}>
                <thead>
                <tr>
                    <th>Задача</th>
                    <th className={'text-center'}>Дата начала</th>
                    <th className={'text-center'}>Дата окончания</th>
                    <th className={'text-center'}>Дата выполнения</th>
                    <th className={'text-center'}>Выполнена</th>
                </tr>
                </thead>
                <tbody>
                {tasks.map(t => (
                    <tr key={t.id}>
                        <td>{t.task}</td>
                        <td className={'text-center'}>{t.start_date}</td>
                        <td className={'text-center'}>{t.end_date}</td>
                        <td className={'text-center'}>{t.completion_date}</td>
                        <td className={'text-center'}>
                            <input type='checkbox' checked={t.completed} disabled/>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}