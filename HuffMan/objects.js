class Element{
    constructor(char_)
    {
        this.char = char_;
        this.freq = 1;
        this.isNode = false;
        this.alpha = 0 ;
    }
}

class Node{
    constructor(leftchild_,rightchild_)
    {
        this.leftchild = leftchild_;
        this.rightchild = rightchild_;

        this.isNode = true;

        this.freq = this.leftchild.freq + this.rightchild.freq;

        this.alpha = 0;
    }
}

class ElementArray {
    constructor(){
        this.elements = [];

        this.eleWidth = 50;
        this.eleHeight = 70;

        this.x = width/4;
        this.y = 25;

        this.y_ = 25;

        this.startShowingTree = false;

        this.i = 0;
        this.j = 0;

        this.nodeY = 0;
        this.nodeVelocity = 0;

        this.alpha = 0;

        this.level = 0;
    }

    add(char_){

        // adding new elements by freq
        for(let ele of this.elements)
        {
            if(char_ == ele.char)
            {
                ele.freq++;
                return;
            }
        }

        //if not in the array
        let newElement = new Element(char_);
        this.elements.push(newElement);

        this.sortArray();

    }

    generateTree(){
        while(this.elements.length>1)
        {
            let left = this.elements.pop();
            let right = this.elements.pop();

            let node = new Node(left,right);

            this.elements.push(node);

            this.sortArray();
        }
    }

    showTree(node_,x_,y_,w_,d_){
        //w-width, d-depth

        if(d_ > ceil(this.level))
            return;
        
        node_.alpha += 10;

        let deltaX = w_ / pow(2, d_ + 1);
        let deltaY = this.eleHeight + 10;

        stroke(255,node_.alpha);

        line(x_,y_ + this.eleWidth/2 , x_ - deltaX , y_ + deltaY);
        line(x_,y_ + this.eleWidth/2 , x_ + deltaX , y_ + deltaY);

        text(0,lerp(x_,x_ - deltaX,0.5),lerp(y_ + this.eleWidth/2,y_ + deltaY,0.75));
        text(1,lerp(x_,x_ + deltaX,0.5),lerp(y_ + this.eleWidth/2,y_ + deltaY,0.75));

        fill(47,89,214,node_.alpha);

        noStroke();

        rect(x_ - this.eleWidth/2 , y_ , this.eleWidth, this.eleWidth);

        fill(255,node_.alpha);

        text(node_.freq,x_,y_ + this.eleWidth/2);

        if(node_.leftchild.isNode){
            this.showTree(node_.leftchild , x_ - deltaX , y_ + deltaY , w_ ,d_ + 1);
        }

        else{
            this.showElement(node_.leftchild , x_ - deltaX , y_ + deltaY , w_ ,d_ + 1)
        }

        if(node_.rightchild.isNode){
            this.showTree(node_.rightchild , x_ + deltaX , y_ + deltaY , w_ ,d_ + 1);
        }

        else{
            this.showElement(node_.rightchild , x_ + deltaX , y_ + deltaY , w_ ,d_ + 1)
        }
    }

    generateNode(){

        if(this.startShowingTree)
            return;

        let right = this.elements[this.elements.length-1];
        let left = this.elements[this.elements.length-2];

        let leftVector = createVector(this.x , this.y + (this.eleHeight *(this.elements.length-2)*1.1));
        let rightVector = createVector(this.x , this.y + (this.eleHeight *(this.elements.length-1)*1.1));

        //idea : we hide the last two real elements and we make imposter elements and it appears they are moving sideways


        //hide elements
        fill('#00071a');

        noStroke();

        rect(leftVector.x - this.eleWidth/2,leftVector.y,this.eleWidth,this.eleHeight);
        rect(rightVector.x - this.eleWidth/2,rightVector.y,this.eleWidth,this.eleHeight);

        // change imposter position
        if(this.j<1)
        {
            leftVector.set( this.interpolate(leftVector.x,width/2 - this.eleWidth - 15, this.j),
                            this.interpolate(leftVector.y,height/2, this.j));
            rightVector.set(this.interpolate(rightVector.x,width/2 + this.eleWidth + 15, this.j),
                            this.interpolate(rightVector.y,height/2, this.j));
        }
        else{
            leftVector.set(width / 2 - this.eleWidth - 15,height/2);
            rightVector.set(width / 2 + this.eleWidth + 15,height/2);
        }
        this.j += 0.005;

        //show imposter element
        this.showElement(right,rightVector.x,rightVector.y + this.nodeY);
        this.showElement(left,leftVector.x,leftVector.y + this.nodeY);

        //creating freq node

        let nodeVector = createVector(width/2 , rightVector.y - this.eleHeight * 1.75 + this.eleWidth/2);

        
        if(this.j>1.5){
            let leftLine = p5.Vector.sub(leftVector,nodeVector);
            let rightLine = p5.Vector.sub(rightVector,nodeVector);

            if(this.j<1.9)
            {
                leftLine.setMag(this.interpolate(0, leftLine.mag(),map(this.j,1.5,1.9,0,1)));
                rightLine.setMag(this.interpolate(0, rightLine.mag(),map(this.j,1.5,1.9,0,1)));
            }

            push();

                translate(nodeVector.x ,nodeVector.y + this.nodeY);
                stroke(255);

                line(0,0,leftLine.x,leftLine.y);
                line(0,0,rightLine.x,rightLine.y);

            pop();

        }   

        //show freq node   
        if(this.j > 1.15)
        {
            fill(47,89,214, map(this.j,1.15,1.3,0,255));
            noStroke();
            rect(nodeVector.x - this.eleWidth/2 ,nodeVector.y - this.eleWidth/2 + this.nodeY,this.eleWidth,this.eleWidth);
        }

        //show freq
        if(this.j>1.9){
            let freq = left.freq + right.freq;

            fill(255,map(this.j,1.9,2.1,0,255));
            text(freq,nodeVector.x ,nodeVector.y + this.nodeY);
        }

        //move and remove this node from existence

        if(this.j>2.3)
        {
            this.nodeY -= this.nodeVelocity;
            this.nodeVelocity += 0.1;
        }

        //done
        if(this.nodeY < -height)
        {
            this.alpha += 10;

            if(this.alpha > 255)
            {
                this.startShowingTree = true;

                for(let i =0; i< this.elements.length;i++)
                {
                    this.elements[i].alpha = 0;
                }
            }
        }
        
        background(0,7,26,this.alpha);
    }

    sortArray(){
        for(let i =0 ;i<this.elements.length; i++)
        {
            for(let j = 0; j<this.elements.length-i-1 ; j++)
            {
                if(this.elements[j].freq < this.elements[j+1].freq)
                {
                    let temp = this.elements[j];
                    this.elements[j] = this.elements[j+1];
                    this.elements[j+1] = temp;
                }
            }
        }
    }

    showElement(ele_,u_,v_,d_){

        if(arguments.length > 3)
        {
            if(d_ > ceil(this.r))
                return;
            
            ele_.alpha += 5;
        }

        //this rectangle shows frequency
        fill(0,166,237,ele_.alpha);
        noStroke();
        rect(u_ - this.eleWidth/2, v_, this.eleWidth,this.eleHeight/2); 

        //this rectangle shows character
        fill(37,52,186,ele_.alpha);
        noStroke();
        rect(u_ - this.eleWidth/2, v_ + this.eleHeight/2, this.eleWidth,this.eleHeight/2); 

        textAlign(CENTER, CENTER);
        textSize(this.eleWidth*0.5);
        fill(255);

        text(ele_.freq, u_, v_ + this.eleHeight/4 );
        text(ele_.char, u_, v_ + this.eleHeight*3/4 );
    }

    interpolate(y1_,y2_,t_){

        let t = (1-cos(t_*PI))/2;
        return y1_ * (1-t) + y2_ * t;
    }

    moveArray()
    {
        if(this.i > 1)
        {
            this.i = 0;
            this.y_ = this.y;
            this.upgrad = true;
            return;
        }

        this.i += 0.007;
        this.y = this.interpolate(this.y_,height/2 - (this.eleHeight*(this.elements.length-1) * 1.1),this.i);
    }

    show(){
        push(); 
            translate(this.x, this.y);

            for(let i = 0 ; i<this.elements.length;i++)
            {
                this.showElement(this.elements[i],0,this.eleHeight*i*1.1);
                this.elements[i].alpha += 255 / (this.elements.length * 7)
            }
        pop();
    }




}