import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 50,        // 50 concurrent users
    duration: '30s' // run for 30 seconds
};

export default function () {

    const payload = JSON.stringify({
        userId: 1,
        showId: 4,
        seatIds: [1]
    });

    const params = {
        headers: {
            'Content-Type': 'application/json'
        }
    };

    let res = http.post('http://localhost:8080/bookings', payload, params);

    check(res, {
        'booking successful': (r) => r.status === 200
    });

    sleep(1);
}