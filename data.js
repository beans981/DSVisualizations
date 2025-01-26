const fs = require('fs');
const path = require('path');

// 定义存储 SWF 文件的目录
const swfDirectory = './2';

// 读取指定目录下的所有文件
fs.readdir(swfDirectory, (err, files) => {
    if (err) {
        console.error('读取目录时出错:', err);
        return;
    }
    const s = '<script src="../swf2js.js"></script><div id="player" style="width: 90vw;height: 90vh;"></div><script>const swf="bbb";swf2js.load(swf, {"tagId": "player"});</script>'
    // 过滤出所有的 SWF 文件
    const swfFiles = files.filter(file => path.extname(file).toLowerCase() === '.swf');

    // 遍历每个 SWF 文件
    swfFiles.forEach(swfFile => {
        const filePath = path.join(swfDirectory, swfFile);

        // 读取文件内容
        fs.readFile(filePath, (err, data) => {
            if (err) {
                console.error(`读取文件 ${swfFile} 时出错:`, err);
                return;
            }

            // 将文件内容转换为 Base64 编码
            const base64Data = data.toString('base64');

            // 打印文件名和对应的 Base64 编码
            console.log(`文件名: ${swfFile}`);
            console.log(`Base64 编码: ${base64Data}`);

            // 你可以选择将 Base64 编码保存到文件中
            const outputFilePath = path.join('./3', `${path.basename(swfFile, '.swf')}.html`);
            fs.writeFile(outputFilePath, '<script src="swf2js.js"></script><div id="player" style="width: 90vw;height: 90vh;"></div><script>const swf="' + base64Data + '";swf2js.load(swf, {"tagId": "player"});</script>'
                , (err) => {
                    if (err) {
                        console.error(`保存 Base64 编码到文件 ${outputFilePath} 时出错:`, err);
                    } else {
                        console.log(`Base64 编码已保存到 ${outputFilePath}`);
                    }
                });
        });
    });
});