// Copyright 2011 David Galles, University of San Francisco. All rights reserved.
//
// Redistribution and use in source and binary forms, with or without modification, are
// permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice, this list of
// conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright notice, this list
// of conditions and the following disclaimer in the documentation and/or other materials
// provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY David Galles ``AS IS'' AND ANY EXPRESS OR IMPLIED
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
// FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> OR
// CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
// SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
// ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
// NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
// ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
//
// The views and conclusions contained in the software and documentation are those of the
// authors and should not be interpreted as representing official policies, either expressed
// or implied, of the University of San Francisco


// Constants.

BST.LINK_COLOR = "#007700";
BST.HIGHLIGHT_CIRCLE_COLOR = "#007700";
BST.FOREGROUND_COLOR = "#007700";
BST.BACKGROUND_COLOR = "#EEFFEE";
BST.PRINT_COLOR = BST.FOREGROUND_COLOR;

BST.WIDTH_DELTA = 50;
BST.HEIGHT_DELTA = 50;
BST.STARTING_Y = 50;


BST.FIRST_PRINT_POS_X = 50;
BST.PRINT_VERTICAL_GAP = 20;
BST.PRINT_HORIZONTAL_GAP = 50;


function BST(am, w, h) {
    this.init(am, w, h);
}

BST.prototype = new Algorithm();
BST.prototype.constructor = BST;
BST.superclass = Algorithm.prototype;

BST.prototype.init = function (am, w, h) {
    this.index = 1
    var sc = BST.superclass;
    this.startingX = w / 2;
    this.first_print_pos_y = h - 2 * BST.PRINT_VERTICAL_GAP;
    this.print_max = w - 10;

    var fn = sc.init;
    fn.call(this, am);
    this.addControls();
    this.nextIndex = 0;
    this.commands = [];
    this.cmd("CreateLabel", 0, "", 20, 10, 0);
    this.nextIndex = 1;
    this.animationManager.StartNewAnimation(this.commands);
    this.animationManager.skipForward();
    this.animationManager.clearHistory();

}

BST.prototype.addControls = function () {
    this.insertField = addControlToAlgorithmBar("Text", "");
    this.insertField.onkeydown = this.returnSubmit(this.insertField, this.insertCallback.bind(this), 4);
    this.insertButton = addControlToAlgorithmBar("Button", "Insert");
    this.insertButton.onclick = this.insertCallback.bind(this);
    // this.deleteField = addControlToAlgorithmBar("Text", "");
    // this.deleteField.onkeydown = this.returnSubmit(this.deleteField, this.deleteCallback.bind(this), 4);
    // this.deleteButton = addControlToAlgorithmBar("Button", "Delete");
    // this.deleteButton.onclick = this.deleteCallback.bind(this);
    // this.findField = addControlToAlgorithmBar("Text", "");
    // this.findField.onkeydown = this.returnSubmit(this.findField, this.findCallback.bind(this), 4);
    // this.findButton = addControlToAlgorithmBar("Button", "Find");
    // this.findButton.onclick = this.findCallback.bind(this);
    this.printButton = addControlToAlgorithmBar("Button", "Print");
    this.printButton.onclick = this.printCallback.bind(this);
    this.ThreadButton = addControlToAlgorithmBar("Button", "Thread");
    this.ThreadButton.onclick = this.ThreadCallback.bind(this);
}

BST.prototype.reset = function () {
    this.nextIndex = 1;
    this.treeRoot = null;
}

BST.prototype.insertCallback = function (event) {
    var insertedValue = this.insertField.value;
    // Get text value
    insertedValue = this.normalizeNumber(insertedValue, 4);

    // set text value
    this.insertField.value = "";
    this.implementAction(this.insertElement.bind(this), insertedValue);

}

BST.prototype.deleteCallback = function (event) {
    var deletedValue = this.deleteField.value;
    if (deletedValue != "") {
        deletedValue = this.normalizeNumber(deletedValue, 4);
        this.deleteField.value = "";
        this.implementAction(this.deleteElement.bind(this), deletedValue);
    }
}
BST.prototype.printCallback = function (event) {
    this.implementAction(this.printTree.bind(this), "");
}

BST.prototype.ThreadCallback = function (event) {
    this.implementAction(this.ThreadTree.bind(this), "");
}

BST.prototype.printTree = function (unused) {
    this.commands = [];

    if (this.treeRoot != null) {
        this.highlightID = this.nextIndex++;
        var firstLabel = this.nextIndex;
        this.cmd("CreateHighlightCircle", this.highlightID, BST.HIGHLIGHT_CIRCLE_COLOR, this.treeRoot.x, this.treeRoot.y);
        this.xPosOfNextLabel = BST.FIRST_PRINT_POS_X;
        this.yPosOfNextLabel = this.first_print_pos_y;
        this.printTreeRec(this.treeRoot);
        this.cmd("Delete", this.highlightID);
        this.cmd("Step")

        for (var i = firstLabel; i < this.nextIndex; i++) {
            this.cmd("Delete", i);
        }
        this.nextIndex = this.highlightID;  /// Reuse objects.  Not necessary.
    }
    return this.commands;
}
// 中序遍历并进行线索化
BST.prototype.createInorderThreadedTree = function (node) {
    if (node !== null) {
        // 递归遍历左子树
        this.createInorderThreadedTree(node.left);
        node.lTag = 0
        node.rTag = 0
        // 处理当前节点
        if (node.left === null) {
            // 左子节点为空，则建立左线索
            node.lTag = 1;
            node.left = this.pre;
            var dir
            if (!this.pre) {
                dir = 22
                
                this.cmd("Connect", node.graphicID, this.pre, BST.LINK_COLOR, -0.22, true, "", dir);
            } else {
                if (this.pre.x > node.x)
                    dir = 34
                else

                    dir = 12
                this.cmd("Connect", node.graphicID, this.pre.graphicID, BST.LINK_COLOR, -0.22, true,
                    "", dir);
            }


        }
        if (this.pre !== null && this.pre.right === null) {
            // 前驱节点的右子节点为空，则建立右线索
            this.pre.rTag = 1;
            this.pre.right = node;
            var dir
            if (this.pre.x > node.x)
                dir = 34
            else
                dir = 12
            this.cmd("Connect", this.pre.graphicID, node.graphicID, BST.LINK_COLOR, 0.22, true, "", dir);

        }
        // 更新前驱节点
        this.pre = node;
        // 递归遍历右子树
        this.createInorderThreadedTree(node.right);
    }
}

// 中序遍历线索二叉树
BST.prototype.inorderThreadedTraversal = function () {
    let node = this.root;
    while (node !== null) {
        // 找到最左边的节点（即中序遍历的第一个节点）
        while (node.lTag === 0) {
            node = node.left;
        }
        // 访问节点
        console.log(node.data);
        // 按照线索找到后继节点
        while (node.rTag === 1 && node.right !== null) {
            node = node.right;
            console.log(node.data);
        }
        // 继续遍历右子树
        node = node.right;
    }
}


BST.prototype.ThreadTree = function (unused) {
    this.commands = [];

    this.pre = null;
    this.createInorderThreadedTree(this.treeRoot);
    this.cmd("Connect", this.pre.graphicID, null, BST.LINK_COLOR, 0.26, true, "", 22);

    return this.commands;
}
BST.prototype.printTreeRec = function (tree) {
    this.cmd("Step");
    if (tree.left != null) {
        this.cmd("Move", this.highlightID, tree.left.x, tree.left.y);
        this.printTreeRec(tree.left);
        this.cmd("Move", this.highlightID, tree.x, tree.y);
        this.cmd("Step");
    }
    var nextLabelID = this.nextIndex++;
    this.cmd("CreateLabel", nextLabelID, tree.data, tree.x, tree.y);
    this.cmd("SetForegroundColor", nextLabelID, BST.PRINT_COLOR);
    this.cmd("Move", nextLabelID, this.xPosOfNextLabel, this.yPosOfNextLabel);
    this.cmd("Step");

    this.xPosOfNextLabel += BST.PRINT_HORIZONTAL_GAP;
    if (this.xPosOfNextLabel > this.print_max) {
        this.xPosOfNextLabel = BST.FIRST_PRINT_POS_X;
        this.yPosOfNextLabel += BST.PRINT_VERTICAL_GAP;

    }
    if (tree.right != null) {
        this.cmd("Move", this.highlightID, tree.right.x, tree.right.y);
        this.printTreeRec(tree.right);
        this.cmd("Move", this.highlightID, tree.x, tree.y);
        this.cmd("Step");
    }
    return;
}

BST.prototype.findCallback = function (event) {
    var findValue;
    findValue = this.normalizeNumber(this.findField.value, 4);
    this.findField.value = "";
    this.implementAction(this.findElement.bind(this), findValue);
}

BST.prototype.findElement = function (findValue) {
    this.commands = [];

    this.highlightID = this.nextIndex++;

    this.doFind(this.treeRoot, findValue);


    return this.commands;
}


BST.prototype.doFind = function (tree, value) {
    this.cmd("SetText", 0, "Searching for " + value);
    if (tree != null) {
        this.cmd("SetHighlight", tree.graphicID, 1);
        if (tree.data == value) {
            this.cmd("SetText", 0, "Searching for " + value + " : " + value + " = " + value + " (Element found!)");
            this.cmd("Step");
            this.cmd("SetText", 0, "Found:" + value);
            this.cmd("SetHighlight", tree.graphicID, 0);
        } else {
            if (tree.data > value) {
                this.cmd("SetText", 0, "Searching for " + value + " : " + value + " < " + tree.data + " (look to left subtree)");
                this.cmd("Step");
                this.cmd("SetHighlight", tree.graphicID, 0);
                if (tree.left != null) {
                    this.cmd("CreateHighlightCircle", this.highlightID, BST.HIGHLIGHT_CIRCLE_COLOR, tree.x, tree.y);
                    this.cmd("Move", this.highlightID, tree.left.x, tree.left.y);
                    this.cmd("Step");
                    this.cmd("Delete", this.highlightID);
                }
                this.doFind(tree.left, value);
            } else {
                this.cmd("SetText", 0, "Searching for " + value + " : " + value + " > " + tree.data + " (look to right subtree)");
                this.cmd("Step");
                this.cmd("SetHighlight", tree.graphicID, 0);
                if (tree.right != null) {
                    this.cmd("CreateHighlightCircle", this.highlightID, BST.HIGHLIGHT_CIRCLE_COLOR, tree.x, tree.y);
                    this.cmd("Move", this.highlightID, tree.right.x, tree.right.y);
                    this.cmd("Step");
                    this.cmd("Delete", this.highlightID);
                }
                this.doFind(tree.right, value);
            }

        }

    } else {
        this.cmd("SetText", 0, "Searching for " + value + " : " + "< Empty Tree > (Element not found)");
        this.cmd("Step");
        this.cmd("SetText", 0, "Searching for " + value + " : " + " (Element not found)");
    }
}

BST.prototype.insertElement = function (insertedValue) {
    this.commands = new Array();
    this.cmd("SetText", 0, "Inserting " + insertedValue);
    this.highlightID = this.nextIndex++;

    if (this.treeRoot == null) {
        this.cmd("CreateCircle", this.nextIndex, insertedValue, this.startingX, BST.STARTING_Y);
        this.cmd("SetForegroundColor", this.nextIndex, BST.FOREGROUND_COLOR);
        this.cmd("SetBackgroundColor", this.nextIndex, BST.BACKGROUND_COLOR);
        this.cmd("Step");
        this.treeRoot = new BSTNode(insertedValue, this.nextIndex, this.startingX, BST.STARTING_Y)
        this.nextIndex += 1;

        this.index += 1
    } else if (insertedValue == "") {
        this.index += 1
    } else {
        this.cmd("CreateCircle", this.nextIndex, insertedValue, 100, 100);
        this.cmd("SetForegroundColor", this.nextIndex, BST.FOREGROUND_COLOR);
        this.cmd("SetBackgroundColor", this.nextIndex, BST.BACKGROUND_COLOR);
        this.cmd("Step");
        var insertElem = new BSTNode(insertedValue, this.nextIndex, 100, 100)


        this.nextIndex += 1;

        this.cmd("SetHighlight", insertElem.graphicID, 1);
        this.insert(insertElem, this.treeRoot)
        this.index += 1
        this.resizeTree();
    }
    this.cmd("SetText", 0, "");
    return this.commands;
}

BST.prototype.insert = function (elem, tree) {
    this.cmd("SetHighlight", tree.graphicID, 1);
    this.cmd("SetHighlight", elem.graphicID, 1);

    this.cmd("Step");
    this.cmd("SetHighlight", tree.graphicID, 0);
    this.cmd("SetHighlight", elem.graphicID, 0);
    if(this.index==10)
    {
        debugger
    }
    var parent = findNodeByIndexInCompleteBinaryTree(tree, Math.trunc(this.index / 2))

    if(parent)
    if (this.index % 2 == 0) {
        this.cmd("SetText", 0, "Found null tree, inserting element");

        this.cmd("SetHighlight", elem.graphicID, 0);
        parent.left = elem;
        elem.parent = parent;
        this.cmd("Connect", parent.graphicID, elem.graphicID, BST.LINK_COLOR);
    } else if (this.index % 2 == 1) {
        this.cmd("SetText", 0, "Found null tree, inserting element");
        this.cmd("SetHighlight", elem.graphicID, 0);
        parent.right = elem;
        elem.parent = parent;
        this.cmd("Connect", parent.graphicID, elem.graphicID, BST.LINK_COLOR);
        elem.x = parent.x + BST.WIDTH_DELTA / 2;
        elem.y = parent.y + BST.HEIGHT_DELTA
        this.cmd("Move", elem.graphicID, elem.x, elem.y);
    }
}

class TreeNode {
    constructor(value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
}

function findNodeByIndexInCompleteBinaryTree(root, index) {

    if (!root) {
        return null;
    }

    const queue = [root]; // 用于层次遍历的队列
    let currentIndex = 0; // 当前访问的节点索引

    while (queue.length > 0) {
        currentIndex++; // 移动到下一个索引
        const currentNode = queue.shift(); // 取出队列中的第一个节点
        if(currentNode) {
            if (currentIndex === index) {
                return currentNode; // 找到第i个节点，返回它
            }
            queue.push(currentNode.left); // 将左子节点加入队列

            queue.push(currentNode.right); // 将右子节点加入队列
        }
        else
        {  queue.push(currentNode);
            queue.push(currentNode);

        }
    }

    return null; // 如果没有找到第i个节点，返回null（理论上不应该发生，除非树的结构有问题或者index超出了树的节点数）
}


BST.prototype.deleteElement = function (deletedValue) {
    this.commands = [];
    this.cmd("SetText", 0, "Deleting " + deletedValue);
    this.cmd("Step");
    this.cmd("SetText", 0, "");
    this.highlightID = this.nextIndex++;
    this.treeDelete(this.treeRoot, deletedValue);
    this.cmd("SetText", 0, "");
    // Do delete
    return this.commands;
}

BST.prototype.treeDelete = function (tree, valueToDelete) {
    var leftchild = false;
    if (tree != null) {
        if (tree.parent != null) {
            leftchild = tree.parent.left == tree;
        }
        this.cmd("SetHighlight", tree.graphicID, 1);
        if (valueToDelete < tree.data) {
            this.cmd("SetText", 0, valueToDelete + " < " + tree.data + ".  Looking at left subtree");
        } else if (valueToDelete > tree.data) {
            this.cmd("SetText", 0, valueToDelete + " > " + tree.data + ".  Looking at right subtree");
        } else {
            this.cmd("SetText", 0, valueToDelete + " == " + tree.data + ".  Found node to delete");
        }
        this.cmd("Step");
        this.cmd("SetHighlight", tree.graphicID, 0);

        if (valueToDelete == tree.data) {
            if (tree.left == null && tree.right == null) {
                this.cmd("SetText", 0, "Node to delete is a leaf.  Delete it.");
                this.cmd("Delete", tree.graphicID);
                if (leftchild && tree.parent != null) {
                    tree.parent.left = null;
                } else if (tree.parent != null) {
                    tree.parent.right = null;
                } else {
                    treeRoot = null;
                }
                this.resizeTree();
                this.cmd("Step");

            } else if (tree.left == null) {
                this.cmd("SetText", 0, "Node to delete has no left child.  \nSet parent of deleted node to right child of deleted node.");
                if (tree.parent != null) {
                    this.cmd("Disconnect", tree.parent.graphicID, tree.graphicID);
                    this.cmd("Connect", tree.parent.graphicID, tree.right.graphicID, BST.LINK_COLOR);
                    this.cmd("Step");
                    this.cmd("Delete", tree.graphicID);
                    if (leftchild) {
                        tree.parent.left = tree.right;
                    } else {
                        tree.parent.right = tree.right;
                    }
                    tree.right.parent = tree.parent;
                } else {
                    this.cmd("Delete", tree.graphicID);
                    this.treeRoot = tree.right;
                    this.treeRoot.parent = null;
                }
                this.resizeTree();
            } else if (tree.right == null) {
                this.cmd("SetText", 0, "Node to delete has no right child.  \nSet parent of deleted node to left child of deleted node.");
                if (tree.parent != null) {
                    this.cmd("Disconnect", tree.parent.graphicID, tree.graphicID);
                    this.cmd("Connect", tree.parent.graphicID, tree.left.graphicID, BST.LINK_COLOR);
                    this.cmd("Step");
                    this.cmd("Delete", tree.graphicID);
                    if (leftchild) {
                        tree.parent.left = tree.left;
                    } else {
                        tree.parent.right = tree.left;
                    }
                    tree.left.parent = tree.parent;
                } else {
                    this.cmd("Delete", tree.graphicID);
                    this.treeRoot = tree.left;
                    this.treeRoot.parent = null;
                }
                this.resizeTree();
            } else // tree.left != null && tree.right != null
            {
                this.cmd("SetText", 0, "Node to delete has two childern.  \nFind largest node in left subtree.");

                this.highlightID = this.nextIndex;
                this.nextIndex += 1;
                this.cmd("CreateHighlightCircle", this.highlightID, BST.HIGHLIGHT_CIRCLE_COLOR, tree.x, tree.y);
                var tmp = tree;
                tmp = tree.left;
                this.cmd("Move", this.highlightID, tmp.x, tmp.y);
                this.cmd("Step");
                while (tmp.right != null) {
                    tmp = tmp.right;
                    this.cmd("Move", this.highlightID, tmp.x, tmp.y);
                    this.cmd("Step");
                }
                this.cmd("SetText", tree.graphicID, " ");
                var labelID = this.nextIndex;
                this.nextIndex += 1;
                this.cmd("CreateLabel", labelID, tmp.data, tmp.x, tmp.y);
                tree.data = tmp.data;
                this.cmd("Move", labelID, tree.x, tree.y);
                this.cmd("SetText", 0, "Copy largest value of left subtree into node to delete.");

                this.cmd("Step");
                this.cmd("SetHighlight", tree.graphicID, 0);
                this.cmd("Delete", labelID);
                this.cmd("SetText", tree.graphicID, tree.data);
                this.cmd("Delete", this.highlightID);
                this.cmd("SetText", 0, "Remove node whose value we copied.");

                if (tmp.left == null) {
                    if (tmp.parent != tree) {
                        tmp.parent.right = null;
                    } else {
                        tree.left = null;
                    }
                    this.cmd("Delete", tmp.graphicID);
                    this.resizeTree();
                } else {
                    this.cmd("Disconnect", tmp.parent.graphicID, tmp.graphicID);
                    this.cmd("Connect", tmp.parent.graphicID, tmp.left.graphicID, BST.LINK_COLOR);
                    this.cmd("Step");
                    this.cmd("Delete", tmp.graphicID);
                    if (tmp.parent != tree) {
                        tmp.parent.right = tmp.left;
                        tmp.left.parent = tmp.parent;
                    } else {
                        tree.left = tmp.left;
                        tmp.left.parent = tree;
                    }
                    this.resizeTree();
                }

            }
        } else if (valueToDelete < tree.data) {
            if (tree.left != null) {
                this.cmd("CreateHighlightCircle", this.highlightID, BST.HIGHLIGHT_CIRCLE_COLOR, tree.x, tree.y);
                this.cmd("Move", this.highlightID, tree.left.x, tree.left.y);
                this.cmd("Step");
                this.cmd("Delete", this.highlightID);
            }
            this.treeDelete(tree.left, valueToDelete);
        } else {
            if (tree.right != null) {
                this.cmd("CreateHighlightCircle", this.highlightID, BST.HIGHLIGHT_CIRCLE_COLOR, tree.x, tree.y);
                this.cmd("Move", this.highlightID, tree.right.x, tree.right.y);
                this.cmd("Step");
                this.cmd("Delete", this.highlightID);
            }
            this.treeDelete(tree.right, valueToDelete);
        }
    } else {
        this.cmd("SetText", 0, "Elemet " + valueToDelete + " not found, could not delete");
    }

}

BST.prototype.resizeTree = function () {
    var startingPoint = this.startingX;
    this.resizeWidths(this.treeRoot);
    if (this.treeRoot != null) {
        if (this.treeRoot.leftWidth > startingPoint) {
            startingPoint = this.treeRoot.leftWidth;
        } else if (this.treeRoot.rightWidth > startingPoint) {
            startingPoint = Math.max(this.treeRoot.leftWidth, 2 * startingPoint - this.treeRoot.rightWidth);
        }
        this.setNewPositions(this.treeRoot, startingPoint, BST.STARTING_Y, 0);
        this.animateNewPositions(this.treeRoot);
        this.cmd("Step");
    }

}

BST.prototype.setNewPositions = function (tree, xPosition, yPosition, side) {
    if (tree != null) {
        tree.y = yPosition;
        if (side == -1) {
            xPosition = xPosition - tree.rightWidth;
        } else if (side == 1) {
            xPosition = xPosition + tree.leftWidth;
        }
        tree.x = xPosition;
        this.setNewPositions(tree.left, xPosition, yPosition + BST.HEIGHT_DELTA, -1)
        this.setNewPositions(tree.right, xPosition, yPosition + BST.HEIGHT_DELTA, 1)
    }

}
BST.prototype.animateNewPositions = function (tree) {
    if (tree != null) {
        this.cmd("Move", tree.graphicID, tree.x, tree.y);
        this.animateNewPositions(tree.left);
        this.animateNewPositions(tree.right);
    }
}

BST.prototype.resizeWidths = function (tree) {
    if (tree == null) {
        return 0;
    }
    tree.leftWidth = Math.max(this.resizeWidths(tree.left), BST.WIDTH_DELTA / 2);
    tree.rightWidth = Math.max(this.resizeWidths(tree.right), BST.WIDTH_DELTA / 2);
    return tree.leftWidth + tree.rightWidth;
}


function BSTNode(val, id, initialX, initialY) {
    this.data = val;
    this.x = initialX;
    this.y = initialY;
    this.graphicID = id;
    this.left = null;
    this.right = null;
    this.parent = null;
}

BST.prototype.disableUI = function (event) {
    this.insertField.disabled = true;
    this.insertButton.disabled = true;
    // this.deleteField.disabled = true;
    // this.deleteButton.disabled = true;
    // this.findField.disabled = true;
    // this.findButton.disabled = true;
    this.printButton.disabled = true;
}

BST.prototype.enableUI = function (event) {
    this.insertField.disabled = false;
    this.insertButton.disabled = false;
    // this.deleteField.disabled = false;
    // this.deleteButton.disabled = false;
    // this.findField.disabled = false;
    // this.findButton.disabled = false;
    this.printButton.disabled = false;
}


var currentAlg;

function init() {
    var animManag = initCanvas();
    currentAlg = new BST(animManag, canvas.width, canvas.height);

}