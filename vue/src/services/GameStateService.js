import axios from 'axios';

export default {
    getGameStateByUsername(){
        return axios.get(`game`)
    }
}