import { registerPublicKeyOnServer } from '../api/key-api.js';
import { exportPublicKeyToSpki, generateKeyPair, getStoredKeyPair, saveKeyPair } from '../core/key-store.js';
import { showNotification } from '../ui/notification.js';

function setCurrentSecurityContext(keyPair) {
    window.chatSecurity = {
        publicKey: keyPair.publicKey,
        privateKey: keyPair.privateKey
    };
}

async function restoreExistingKeys() {
    const storedKeyPair = await getStoredKeyPair();

    if (!storedKeyPair) {
        return false;
    }

    setCurrentSecurityContext(storedKeyPair);
    showNotification('Ключи успешно загружены из IndexedDB.', 'success');
    return true;
}

async function createAndRegisterNewKeys() {
    const generatedKeyPair = await generateKeyPair();
    const publicKeyBuffer = await exportPublicKeyToSpki(generatedKeyPair.publicKey);
    const registrationResult = await registerPublicKeyOnServer(publicKeyBuffer);

    if (!registrationResult.ok) {
        showNotification(registrationResult.message, 'error', 7000);
        return;
    }

    await saveKeyPair(generatedKeyPair);
    setCurrentSecurityContext(generatedKeyPair);
    showNotification(registrationResult.message, 'success');
}

async function initializeChatPage() {
    try {
        const hasRestoredKeys = await restoreExistingKeys();

        if (hasRestoredKeys) {
            return;
        }

        await createAndRegisterNewKeys();
    } catch (error) {
        const message = error instanceof Error && error.message
            ? error.message
            : 'Произошла неизвестная ошибка при инициализации ключей.';

        showNotification(message, 'error', 7000);
    }
}

document.addEventListener('DOMContentLoaded', initializeChatPage);
