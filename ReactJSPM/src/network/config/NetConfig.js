/**
 * Created by chris on 2017/8/11.
 */
import config from 'config';

const BaseDevUrl = 'http://localhost:12001/';
const BaseDistUrl = 'https://appmgr.vd.cn/';

export default BaseUrl => {
    let url = BaseDistUrl;
    if (config.appEnv === 'dev') {
        url = BaseDevUrl;
    }
    return url;
}
