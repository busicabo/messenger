export async function registerPublicKeyOnServer(publicKeyBuffer) {
    let response;

    try {
        response = await fetch('/message/api/new_key', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/octet-stream'
            },
            body: publicKeyBuffer
        });
    } catch (error) {
        return {
            ok: false,
            code: -1,
            message: 'Ошибка сети при отправке публичного ключа на сервер.'
        };
    }

    if (!response.ok) {
        return {
            ok: false,
            code: response.status,
            message: `Сервер вернул ошибку ${response.status}.`
        };
    }

    let payload;

    try {
        payload = await response.json();
    } catch (error) {
        return {
            ok: false,
            code: -2,
            message: 'Сервер вернул некорректный JSON-ответ.'
        };
    }

    const serverMessage = typeof payload?.message === 'string' && payload.message.trim()
        ? payload.message.trim()
        : 'Сервер не прислал описание результата.';

    const isSuccess = payload?.success === true || payload?.code === 0;

    return {
        ok: isSuccess,
        code: typeof payload?.code === 'number' ? payload.code : -3,
        message: serverMessage,
        raw: payload
    };
}
