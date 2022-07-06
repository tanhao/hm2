let ws_protocol = 'wss'; // ws 或 wss
let ip = '1249.67888288.co/ws/'
let port = 24010
import tio from './tiows'
export default {
  initWs(uid,handler) {
    let paramStr = 'uid=' + uid;
    let host = location.host;//location.host。。。document.domain
    if (host !== 'localhost') {
      if (host.split(".")[0] !== '192') {
        port = ''
        ip = host+'/ws/';
      }
    }
    let tiows = new tio.ws(ws_protocol, ip, port, paramStr, handler)
    tiows.connect()
    return tiows;
  }
}


