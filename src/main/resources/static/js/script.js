// нет проверки на кол-во попыток

let secretNumber;
let attempts = 0;
let maxAttempts;
let maxCompletionTime;
let timerInterval;
let startTime;

function generateSecretNumber() {
    let digits = [];
    while (digits.length < 4) {
        let digit = Math.floor(Math.random() * 10);
        if (!digits.includes(digit)) {
            digits.push(digit);
        }
    }
    console.log(digits)
    return digits.join('');
}

function startGame() {
    secretNumber = generateSecretNumber();
    attempts = 0;
    maxCompletionTime = parseInt(document.getElementById('maxCompletionTime').value);
    maxAttempts = parseInt(document.getElementById('maxAttempts').value)
    document.getElementById('attempts').textContent = attempts;
    document.getElementById('time').textContent = 0;
    document.getElementById('results').innerHTML = '';

    startTime = Date.now();
    timerInterval = setInterval(() => {
        let elapsed = Math.floor((Date.now() - startTime) / 1000);
        document.getElementById('time').textContent = elapsed;

        if (elapsed >= maxCompletionTime) {
            alert(`Время вышло! Загаданное число было ${secretNumber}`);
            clearInterval(timerInterval);
        }
    }, 1000);

    $('#startGameModal').modal('hide');
}

function makeGuess() {
    let guess = '';
    for (let i = 1; i <= 4; i++) {
        guess += document.getElementById(`digit${i}`).value;
    }

    if (guess.length !== 4 || new Set(guess).size !== 4) {
        alert('Введите 4 уникальные цифры');
        return;
    }

    attempts++;
    document.getElementById('attempts').textContent = attempts;

    let { bulls, cows } = getBullsAndCows(guess, secretNumber);
    document.getElementById('bulls').value = bulls;
    document.getElementById('cows').value = cows;
    displayResult(guess, bulls, cows);

    if (bulls === 4) {
        alert(`Вы угадали число ${secretNumber} за ${attempts} попыток!`);
        clearInterval(timerInterval);
        saveGameStatistics();
    }
}

function getBullsAndCows(guess, secret) {
    let bulls = 0, cows = 0;
    for (let i = 0; i < 4; i++) {
        if (guess[i] === secret[i]) {
            bulls++;
        } else if (secret.includes(guess[i])) {
            cows++;
        }
    }
    return { bulls, cows };
}

function displayResult(guess, bulls, cows) {
    let results = document.getElementById('results');
    let result = document.createElement('div');
    result.textContent = `${guess} - ${bulls}Б${cows}К`;
    results.appendChild(result);
}

function saveGameStatistics() {
    const apiUrl = '/api/game-stat';

    const gameStatistics = {
        attempts: attempts,
        completionTime: Math.floor((Date.now() - startTime) / 1000),
        maxAttempts: maxAttempts,
        maxCompletionTime: maxCompletionTime
    };

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify(gameStatistics)
    };


    fetch(apiUrl, requestOptions)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            console.log(response)
            return response.json();
        })
        .then((data) => console.log(data))
        .catch(error => {
            console.error('Error saving game statistics:', error);
        });
}