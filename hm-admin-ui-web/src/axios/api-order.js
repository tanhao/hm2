import HttpRequest from './index'

const axios = new HttpRequest()

export default {
    getDetailTimeCount(data) {
        return axios.requestPost('/v2/order/ag/time/count', data);
    },
    getOrderList(data) {
        return axios.requestPost('/v2/order/ag/list', data);
    },
    getOrderById(orderId) {
        return axios.requestGet('/v2/order/ag/id/' + orderId);
    },
    getGameNo(day, lotteryId) {
        return axios.requestGet('/v2/order/ag/gameno/' + day + '/' + lotteryId);
    },
    voidOrder(data) {
        return axios.requestPost('/v2/order/ag/void/order', data);
    },
    getWinLose(data) {
        return axios.requestPost('/v2/order/ag/winlose', data, 50000);
    },
    getWinLoseByLottery(data) {
        return axios.requestPost('/v2/order/ag/winlose/lottery', data, 50000);
    },
    getWinLoseByKind(data) {
        return axios.requestPost('/v2/order/ag/winlose/kind', data, 50000);
    },
};
