const notificationElement = document.getElementById('notification');
let hideTimerId = null;

export function showNotification(message, type = 'success', duration = 5000) {
    if (!notificationElement) {
        return;
    }

    notificationElement.textContent = message;
    notificationElement.className = `notification notification--visible notification--${type}`;

    window.clearTimeout(hideTimerId);
    hideTimerId = window.setTimeout(hideNotification, duration);
}

export function hideNotification() {
    if (!notificationElement) {
        return;
    }

    notificationElement.classList.remove('notification--visible');
}
