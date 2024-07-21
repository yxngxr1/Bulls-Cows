let gameIsRun = false;
let startTime;
let timerInterval;
let secretNumber;
async function startGame() {
    const maxCompletionTime = parseInt(document.getElementById('maxCompletionTime').value);
    const maxAttempts = parseInt(document.getElementById('maxAttempts').value);

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    const response = await fetch('/api/start-game', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify({ maxCompletionTime, maxAttempts })
    });
    if (!response.ok)
    {
        throw new Error(`HTTP error! Status: ${response.status}`);
    }
    const data = await response.json();
    console.log(data);

    document.getElementById('digit1').value = '';
    document.getElementById('digit2').value = '';
    document.getElementById('digit3').value = '';
    document.getElementById('digit4').value = '';
    document.getElementById('bulls').value = '';
    document.getElementById('cows').value = '';
    document.getElementById('attempts').textContent = 0;
    document.getElementById('time').textContent = 0;
    document.getElementById('results').innerHTML = '';
    gameIsRun = true;
    startTime = Date.now();

    timerInterval = setInterval(() => {
        let elapsed = Math.floor((Date.now() - startTime) / 1000);
        document.getElementById('time').textContent = elapsed;


        if (maxCompletionTime != 121 && elapsed >= maxCompletionTime) {
            stopGame();
            alert(`Время вышло загаданным числом было ${secretNumber}!`);
            return;
        }
    }, 1000);

    $('#startGameModal').modal('hide');
}

async function makeGuess() {
    if (!gameIsRun) {
        alert(`Для начала игры нажмите кнопку "Начать"`);
        return;
    }

    let guess = '';
    for (let i = 1; i <= 4; i++) {
        guess += document.getElementById(`digit${i}`).value;
    }

    if (guess.length !== 4 || new Set(guess).size !== 4) {
        alert('Введите 4 уникальные цифры');
        return;
    }

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    const response = await fetch('/api/make-guess', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify({ guess })
    });
    const data = await response.json();
//    console.log(data);
    if (!response.ok)
    {
        throw new Error(`HTTP error! Status: ${response.status}`);
    }

    document.getElementById('bulls').value = data.bulls;
    document.getElementById('cows').value = data.cows;
    document.getElementById('attempts').textContent = data.attempts;
    secretNumber = data.secretNumber;
    displayResult(guess, data.bulls, data.cows);

    if (data.isWin) {
        stopGame();
        alert(`Вы угадали число за ${data.attempts} попыток!`);
        return;
    }
    if (data.attempts > data.maxAttempts) {
        stopGame();
        alert(`Превышено количество попыток!`);
        return;
    }

    document.getElementById('digit1').value = '';
    document.getElementById('digit2').value = '';
    document.getElementById('digit3').value = '';
    document.getElementById('digit4').value = '';
    document.getElementById('digit1').focus();
}

function displayResult(guess, bulls, cows) {
    let results = document.getElementById('results');
    let result = document.createElement('div');
    result.textContent = `${guess} - ${bulls}Б${cows}К`;
    results.appendChild(result);
}

function stopGame() {
    clearInterval(timerInterval);
    gameIsRun = false;
}