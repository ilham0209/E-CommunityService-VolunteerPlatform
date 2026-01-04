/**
 * Validate email format
 */
function validateEmail(email) {
    const emailRegex = /^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
    return emailRegex.test(email);
}

/**
 * Validate password length
 */
function validatePassword(password) {
    return password.length >= 6;
}

/**
 * Validate matric number format (10 digits)
 */
function validateMatricNumber(matricNumber) {
    return /^\d{10}$/.test(matricNumber);
}

/**
 * Validate IC number format (YYMMDD-PB-####)
 */
function validateICNumber(icNumber) {
    return /^\d{6}-\d{2}-\d{4}$/.test(icNumber);
}

/**
 * Validate phone number (10-11 digits)
 */
function validatePhoneNumber(phoneNumber) {
    return /^\d{10,11}$/.test(phoneNumber);
}

/**
 * Show validation error message
 */
function showError(inputElement, message) {
    const errorDiv = document.createElement('div');
    errorDiv.className = 'invalid-feedback d-block';
    errorDiv.textContent = message;
    
    // Remove existing error
    const existingError = inputElement.parentElement.querySelector('.invalid-feedback');
    if (existingError) {
        existingError.remove();
    }
    
    // Add new error
    inputElement.classList.add('is-invalid');
    inputElement.parentElement.appendChild(errorDiv);
}

/**
 * Clear validation error
 */
function clearError(inputElement) {
    inputElement.classList.remove('is-invalid');
    const errorDiv = inputElement.parentElement.querySelector('.invalid-feedback');
    if (errorDiv) {
        errorDiv.remove();
    }
}

/**
 * Real-time email validation
 */
function setupEmailValidation(emailInputId) {
    const emailInput = document.getElementById(emailInputId);
    if (emailInput) {
        emailInput.addEventListener('blur', function() {
            if (this.value && !validateEmail(this.value)) {
                showError(this, 'Invalid email format');
            } else {
                clearError(this);
            }
        });
    }
}

/**
 * Real-time password validation
 */
function setupPasswordValidation(passwordInputId) {
    const passwordInput = document.getElementById(passwordInputId);
    if (passwordInput) {
        passwordInput.addEventListener('input', function() {
            if (this.value && !validatePassword(this.value)) {
                showError(this, 'Password must be at least 6 characters');
            } else {
                clearError(this);
            }
        });
    }
}