class LocalStorageService {
    /**
     * 将对象序列化为字符串并存储到 localStorage 中
     * @param {string} key 存储的键
     * @param {*} value 存储的值（可以是任意类型）
     */
    setItem(key, value) {
        try {
            let serializedValue;
            if ((typeof value === 'object' || Array.isArray(value)) && value !== null) {
                // 对象（非数组、非函数）进行序列化
                serializedValue = JSON.stringify(value);
            } else {
                // 其他类型直接存储
                serializedValue = value;
            }
            localStorage.setItem(key, serializedValue);
        } catch (error) {
            console.error('存储数据到 localStorage 失败:', error);
        }
    }

    /**
     * 从 localStorage 中获取字符串并反序列化为对象
     * @param {string} key 存储的键
     * @returns {*} 存储的值（可以是任意类型）
     */
    getItem(key) {
        try {
            const serializedValue = localStorage.getItem(key);
            if (serializedValue === null) {
                return null;
            }
            try {
                // 尝试反序列化
                return JSON.parse(serializedValue);
            } catch (parseError) {
                // 如果反序列化失败，说明存储的是非对象类型
                return serializedValue;
            }
        } catch (error) {
            console.error('从 localStorage 获取数据失败:', error);
            return null;
        }
    }

    /**
     * 从 localStorage 中移除指定的键
     * @param {string} key 存储的键
     */
    removeItem(key) {
        localStorage.removeItem(key);
    }

    /**
     * 清空 localStorage
     */
    clear() {
        localStorage.clear();
    }
}

class DateTimeUtils {
    /**
     * 将毫秒时间戳格式化为时间字符串
     * @param {number} timestamp - 毫秒时间戳
     * @param {string} format - 时间格式字符串，支持以下格式：
     *   - 'YYYY-MM-DD HH:mm:ss'
     *   - 'YYYY-MM-DD'
     *   - 'HH:mm:ss'
     * @returns {string} 格式化后的时间字符串
     */
    formatTimestamp(timestamp, format = 'YYYY-MM-DD HH:mm:ss') {
        const time = parseInt(timestamp)
        const date = new Date(time);
        const year = date.getFullYear();
        const month = this.padZero(date.getMonth() + 1);
        const day = this.padZero(date.getDate());
        const hours = this.padZero(date.getHours());
        const minutes = this.padZero(date.getMinutes());
        const seconds = this.padZero(date.getSeconds());

        return format
            .replace('YYYY', year)
            .replace('MM', month)
            .replace('DD', day)
            .replace('HH', hours)
            .replace('mm', minutes)
            .replace('ss', seconds);
    }

    /**
     * 将秒时间戳格式化为时间字符串
     * @param {number} timestamp - 秒时间戳
     * @param {string} format - 时间格式字符串，支持以下格式：
     *   - 'YYYY-MM-DD HH:mm:ss'
     *   - 'YYYY-MM-DD'
     *   - 'HH:mm:ss'
     * @returns {string} 格式化后的时间字符串
     */
    formatSecondsTimestamp(timestamp, format = 'YYYY-MM-DD HH:mm:ss') {
        const time = parseInt(timestamp)

        const milliseconds = time * 1000;
        return this.formatTimestamp(milliseconds, format);
    }

    /**
     * 补零函数
     * @param {number} num - 需要补零的数字
     * @returns {string} 补零后的字符串
     */
    padZero(num) {
        return num.toString().padStart(2, '0');
    }
}






// 单例模式，确保全局只有一个实例
const localStorageService = new LocalStorageService();
const dateTimeUtils = new DateTimeUtils();

export function handleStr(str) {
    return str.substring(0, 10) + '.....'
}

export { localStorageService, dateTimeUtils };
