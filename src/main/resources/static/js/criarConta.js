const inputSenha = document.getElementById('password');
const reqLength = document.getElementById('req-length');
const reqUpper = document.getElementById('req-upper');
const reqNumber = document.getElementById('req-number');
const reqSpecial = document.getElementById('req-special');

inputSenha.addEventListener('input', function () {
    const value = this.value;

    // 1. Testa comprimento mínimo
    toggleStatus(reqLength, value.length >= 8);

    // 2. Testa letra maiúscula
    toggleStatus(reqUpper, /[A-Z]/.test(value));

    // 3. Testa número
    toggleStatus(reqNumber, /[0-9]/.test(value));

    // 4. Testa caractere especial
    toggleStatus(reqSpecial, /[^A-Za-z0-9]/.test(value));
});

// Função utilitária que adiciona/remove a classe 'valid'
function toggleStatus(element, isValid) {
    if (isValid) {
        element.classList.add('valid');
    } else {
        element.classList.remove('valid');
    }
}
