// 定义 ListArray 类
class ListArray {
    constructor() {
        this.list = [];
        this.canvas = document.getElementById('canvas');
        this.ctx = this.canvas.getContext('2d');
        this.elementWidth = 50;
        this.elementHeight = 50;
        this.startX = 50;
        this.startY = 200;
    }

    // 插入元素
    insertElement() {
        const inputElement = document.getElementById('elementToInsert');
        const value = inputElement.value;
        if (value) {
            this.list.push(value);
            inputElement.value = '';
            this.drawList();
        }
    }

    // 删除元素
    deleteElement() {
        const inputElement = document.getElementById('elementToDelete');
        const value = inputElement.value;
        if (value) {
            const index = this.list.indexOf(value);
            if (index !== -1) {
                this.list.splice(index, 1);
                inputElement.value = '';
                this.drawList();
            }
        }
    }

    // 绘制列表元素
    drawList() {
        this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
        for (let i = 0; i < this.list.length; i++) {
            const x = this.startX + i * (this.elementWidth + 20);
            const y = this.startY;
            this.ctx.fillStyle = 'lightblue';
            this.ctx.fillRect(x, y, this.elementWidth, this.elementHeight);
            this.ctx.fillStyle = 'black';
            this.ctx.font = '20px Arial';
            this.ctx.fillText(this.list[i], x + 10, y + 30);
        }
    }
}

// 初始化列表
let listArray = new ListArray();
function init() {
    listArray.drawList();
}