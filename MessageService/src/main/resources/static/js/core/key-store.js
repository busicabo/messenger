import { loadStoredKeyPairRecord, saveStoredKeyPairRecord } from './indexeddb.js';

const KEY_ALGORITHM = {
    name: 'RSA-OAEP',
    modulusLength: 2048,
    publicExponent: new Uint8Array([1, 0, 1]),
    hash: 'SHA-256'
};

const KEY_USAGES = {
    publicKey: ['encrypt'],
    privateKey: ['decrypt']
};

function ensureCryptoSupport() {
    if (!window.crypto?.subtle) {
        throw new Error('Браузер не поддерживает Web Crypto API.');
    }
}

export async function generateKeyPair() {
    ensureCryptoSupport();

    return window.crypto.subtle.generateKey(
        KEY_ALGORITHM,
        true,
        ['encrypt', 'decrypt']
    );
}

export async function exportPublicKeyToSpki(publicKey) {
    ensureCryptoSupport();
    return window.crypto.subtle.exportKey('spki', publicKey);
}

export async function exportKeyPairToJwkRecord(keyPair) {
    ensureCryptoSupport();

    const publicKeyJwk = await window.crypto.subtle.exportKey('jwk', keyPair.publicKey);
    const privateKeyJwk = await window.crypto.subtle.exportKey('jwk', keyPair.privateKey);

    return {
        public_key: publicKeyJwk,
        private_key: privateKeyJwk,
        saved_at: new Date().toISOString()
    };
}

export async function importKeyPairFromJwkRecord(record) {
    ensureCryptoSupport();

    if (!record?.public_key || !record?.private_key) {
        throw new Error('В IndexedDB нет полного набора ключей.');
    }

    const publicKey = await window.crypto.subtle.importKey(
        'jwk',
        record.public_key,
        KEY_ALGORITHM,
        true,
        KEY_USAGES.publicKey
    );

    const privateKey = await window.crypto.subtle.importKey(
        'jwk',
        record.private_key,
        KEY_ALGORITHM,
        true,
        KEY_USAGES.privateKey
    );

    return { publicKey, privateKey };
}

export async function getStoredKeyPair() {
    const storedRecord = await loadStoredKeyPairRecord();

    if (!storedRecord?.public_key || !storedRecord?.private_key) {
        return null;
    }

    return importKeyPairFromJwkRecord(storedRecord);
}

export async function saveKeyPair(keyPair) {
    const record = await exportKeyPairToJwkRecord(keyPair);
    await saveStoredKeyPairRecord(record);
}
