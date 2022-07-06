const INIT_LOTTERY = 'INIT_LOTTERY';
const INIT_LOTTERYS = 'INIT_LOTTERYS';

export default {
    state: {
        lottery: {},
        lotterys: [],
        kinds: {},
        categorys: {},
        plays: {},
        oddss: {},
        userOddds: {},
        userOddsNows: {},
        userOddsJumps: {},
        userOddsCljps: {},
        userOddsCloses: {},
    },
    mutations: {
        [INIT_LOTTERY] (state, lottery) {
            state.lottery = lottery;
        },
        [INIT_LOTTERYS] (state, lotterys) {
            state.lotterys = lotterys;
        },
    },
    actions: {
        setLottery ({ commit }, lottery) {
            commit(INIT_LOTTERY, lottery);
        },
        setLotterys ({ commit }, lotterys) {
            commit(INIT_LOTTERYS, lotterys);
        },
    },
    getters: {
        lottery: state => state.lottery,
        lotterys: state => state.lotterys,
    },
}