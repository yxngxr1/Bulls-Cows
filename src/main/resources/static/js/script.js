let secretNumber = generateSecretNumber();
let attempts = 0;
let maxTime = 300; // 5 минут
let startTime = Date.now();

function generateSecretNumber() {
    let digits = [];
    while (digits.length < 4) {
        let digit = Math.floor(Math.random() * 10);
        if (!digits.includes(digit)) {
            digits.push(digit);
        }
    }
    return digits.join('');
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

let timerInterval = setInterval(() => {
    let elapsed = Math.floor((Date.now() - startTime) / 1000);
    document.getElementById('time').textContent = elapsed;

    if (elapsed >= maxTime) {
        alert(`Время вышло! Загаданное число было ${secretNumber}`);
        clearInterval(timerInterval);
    }
}, 1000);