const DATABASE_NAME = 'mescat_secure_storage';
const DATABASE_VERSION = 1;
const STORE_NAME = 'app_pairs';
const RECORD_KEY = 'current_key_pair';

function openDatabase() {
    return new Promise((resolve, reject) => {
        if (!('indexedDB' in window)) {
            reject(new Error('Браузер не поддерживает IndexedDB.'));
            return;
        }

        const request = window.indexedDB.open(DATABASE_NAME, DATABASE_VERSION);

        request.onupgradeneeded = (event) => {
            const database = event.target.result;

            if (!database.objectStoreNames.contains(STORE_NAME)) {
                database.createObjectStore(STORE_NAME);
            }
        };

        request.onsuccess = () => resolve(request.result);
        request.onerror = () => reject(request.error || new Error('Не удалось открыть IndexedDB.'));
    });
}

function withStore(mode, callback) {
    return openDatabase().then((database) => new Promise((resolve, reject) => {
        const transaction = database.transaction(STORE_NAME, mode);
        const store = transaction.objectStore(STORE_NAME);

        let request;

        try {
            request = callback(store);
        } catch (error) {
            reject(error);
            database.close();
            return;
        }

        transaction.oncomplete = () => {
            database.close();
            resolve(request?.result);
        };

        transaction.onerror = () => {
            database.close();
            reject(transaction.error || new Error('Ошибка транзакции IndexedDB.'));
        };

        transaction.onabort = () => {
            database.close();
            reject(transaction.error || new Error('Транзакция IndexedDB была прервана.'));
        };
    }));
}

export function loadStoredKeyPairRecord() {
    return withStore('readonly', (store) => store.get(RECORD_KEY));
}

export function saveStoredKeyPairRecord(record) {
    return withStore('readwrite', (store) => store.put(record, RECORD_KEY));
}

export function clearStoredKeyPairRecord() {
    return withStore('readwrite', (store) => store.delete(RECORD_KEY));
}
