const n = 5; // количество строк таблицы
const table = document.getElementById('myTable');

for (let i = 1; i <= n; i++) {
    const id = i;
    const type = 'Тип игры';
    const tryCount = Math.floor(Math.random() * 10);
    const time = new Date().toLocaleTimeString();
    const win = Math.random() < 0.5;

    const row = table.insertRow();
    row.classList.add('statistic')
    row.insertCell().textContent = id;
    row.lastElementChild.classList.add('col_id');
    row.insertCell().textContent = type;
    row.lastElementChild.classList.add('col_type');
    row.insertCell().textContent = tryCount;
    row.lastElementChild.classList.add('col_try');
    row.insertCell().textContent = time;
    row.lastElementChild.classList.add('col_time');

    if (win) {
        //row.insertCell().textContent = 'Win';
        row.classList.add('table-success');
    } else {
        //row.insertCell().textContent = 'Lose';
        row.classList.add('table-danger');
    }
}