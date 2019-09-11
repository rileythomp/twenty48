function get_random_coord() {
    row = Math.floor(Math.random() * 4);
    // col = Math.floor(Math.random() * 4);
    col = 1;
}

function coord_has_number() {
    return board.children[row].children[col].children.length > 0;
}

function update_cell_view(n) {
    let cell_val = document.createElement('span');
    cell_val.innerHTML = n;
    board.children[row].children[col].appendChild(cell_val);
}

function init_cells() {
    get_random_coord();

    while (coord_has_number()) {
        get_random_coord();
    }

    update_cell_view(2);
}

function start_drag(ev) {
    xStart = ev.clientX;
    yStart = ev.clientY;
}

function end_drag(ev) {
    xEnd = ev.clientX;
    yEnd = ev.clientY;

    move_cells();
}

function abs(x) {
    return (x > 0 ? x : -1*x);
}

// 1 is up
// 2 is right
// 3 is down
// 4 is left

function determine_direction() {
    deltaX = xEnd - xStart;
    deltaY = yEnd - yStart;

    if (abs(deltaY) > abs(deltaX) && deltaY < 0) {
        direction = 1;
    }
    if (abs(deltaY) > abs(deltaX) && deltaY > 0) {
        direction = 3;
    }
    if (abs(deltaX) > abs(deltaY) && deltaX > 0) {
        direction = 2;
    }
    if (abs(deltaX) > abs(deltaY) && deltaX < 0) {
        direction = 4;
    }
}

function board_cell(row, col) {
    return board.children[row].children[col];
}

function move_cells() {
    determine_direction();

    switch (direction) {
        case 1:
            console.log("up");
            for (let col = 0; col < 4; ++col) {
                for (let row = 3; row >= 0; --row) {
                    for (let i = row-1; i >= 0; --i) {
                        if (board_cell(row, col).children.length != 0 && board_cell(i, col).children.length == 0) {
                            // swap this cell with cur cell
                            let cell_val = board_cell(row, col).children[0];
                            board_cell(row, col).removeChild(cell_val);
                            board_cell(i, col).appendChild(cell_val);
                            break;
                        }
                        else if (board_cell(row, col).children.length != 0 && board_cell(row, col).children[0].innerHTML == board_cell(i, col).children[0].innerHTML) {
                            let cell_val = board_cell(row, col).children[0];
                            board_cell(row, col).removeChild(cell_val);
                            board_cell(i, col).children[0].innerHTML = 2*Number(board_cell(i, col).children[0].innerHTML);
                            break;
                        }
                    }

                }
            }


            break;
        case 2:
            console.log("right");
            break;
        case 3:
            console.log("down");
            break;
        case 4:
            console.log("left");
            break;
    }
}

// "main" function

let direction;

let row;
let col;

let xStart;
let yStart;

let xEnd;
let yEnd;

let board = document.getElementById('board');

for (let i = 0; i < 4; ++i) {
    let row = document.createElement('tr');
    row.setAttribute('class', 'row');
    row.setAttribute('id', 'row' + i);

    for (let j = 0; j < 4; ++j) {
        let cell = document.createElement('td');
        cell.setAttribute('class', 'cell');
        cell.setAttribute('id', 'cell' + j);
        // cell.addEventListener('dragstart', move_cells);

        row.appendChild(cell);
    }

    board.appendChild(row);
}

init_cells();
init_cells();
init_cells();
init_cells();

board.addEventListener('mousedown', start_drag);
board.addEventListener('mouseup', end_drag);



