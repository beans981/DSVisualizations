let input = "willy wonka"
let startHuffmanCode = false;
let startCount = 0;
let elementArray;
let textAlpha = 255;
let textCount = 0;
let index = 0;


function setup(){
    createCanvas(windowWidth,windowHeight);

    elementArray = new ElementArray();
    
}

function draw(){
    background('#00071a');

    if(startHuffmanCode)
    {
        // start delay
        if(startCount > 50)
        {
            if(!elementArray.startShowingTree)
            {
                //draw text
                if(textAlpha>=1)
                {
                    let textString = "";

                    for(let i = 0 ; i < textCount ; i++)
                    {
                        textString += input.charAt(i);
                    }
                    textFont('JetBrains Mono');
                    fill(255,textAlpha);
                    textSize(25);

                    text(textString, width/2, height/2, width/2 - 50);

                    textCount+=0.5;
                }
                // add elements to the array and sort
                if(input.length < textCount - 35)
                {
                    if(index < input.length)
                    {
                        if(frameCount % 5 == 0)
                        {
                            elementArray.add(input.charAt(index));
                            index++;
                        }
                    }
                    elementArray.show();
                }

                // fade preview text
                if(index == input.length)
                {
                    textAlpha -= 10;
                }

                //when preview text fades, we move the array up
                if(textAlpha <= -15)
                {
                   elementArray.moveArray();
                }  

                
                //we now take last two values to create a node
                if(elementArray.upgrad)
                {
                    elementArray.generateNode();
                }
                
            }
            else{
                elementArray.generateTree();
                elementArray.level +=0.05;
                elementArray.showTree(elementArray.elements[0],width/2,35,width/1.15,1);
            }
            
        }
        else
        {
            startCount++;
        }
    }
}

function startHuffman(){
    
    let textArea = document.getElementById("textArea");
    let button = document.getElementById("runButton");

    if(!textArea.classList.contains("hideTextArea"))
    {
        textArea.classList.add("hideTextArea");
        button.classList.add("hideTextArea");
    }

    startHuffmanCode = true;

    input = textArea.value;

}

function windowResized(){
    resizeCanvas(windowWidth,windowHeight);

    elementArray.x = width/4;
}