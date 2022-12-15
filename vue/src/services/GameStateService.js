import axios from 'axios';

export default {
    getGameStateByUsername(username){
        return axios.get(`examples/${username}`)
    }
}