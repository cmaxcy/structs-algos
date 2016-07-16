"""
    Tic Tac Toe PyGame GUI
    User can play an unbeatable AI tic tac toe player
    User always starts
"""

import pygame
import random

# responsible for all back end move calculation
class TicTac:

    # score constants
    MAX_SCORE = 100
    DRAW_SCORE = 0
    MIN_SCORE = -100

    def __init__(self):

        # dictionary stores moves as keys, and their scores as values (move: score)
        self.known_scores = {}

        # game is run through to populate dictionary
        self.CPUvsCPU()

    @staticmethod
    # outputs a string version of the grid with the player at the end
    def move_to_string(the_grid, the_player):

        move_string = ""

        # for each location on the grid
        for row in the_grid:
            for char in row:

                # add the location to move_string
                move_string += char

        # add the player to the end
        move_string += the_player

        return move_string

    @staticmethod
    # counts the number blank (unfilled) spots in the board passed
    def get_space_count(the_grid):

        space_count = 0

        # for each location in the gird
        for row in the_grid:
            for spot in row:

                # if this location is a space, increment space_count
                if spot is " ":
                    space_count += 1

        return space_count

    @staticmethod
    # returns the state of the board passed
    # X: X wins, O: O wins, D: game is a draw, U: game is unfinished
    def get_game_state(the_grid):

        # checks if the player passed has won the game
        def has_player_won(player):

            # check rows
            for row in the_grid:

                # if entire row is player
                if row[0] is player and row[1] is player and row[2] is player:
                    return True

            # check columns
            for i in range(3):

                # if entire column is player
                if the_grid[0][i] is player and the_grid[1][i] is player and the_grid[2][i] is player:
                    return True

            # check diagonal: \
            if the_grid[0][0] is player and the_grid[1][1] is player and the_grid[2][2] is player:
                return True

            # check diagonal: /
            if the_grid[2][0] is player and the_grid[1][1] is player and the_grid[0][2] is player:
                return True

            # else player must not have won
            return False

        # count un-played spaces
        space_count = TicTac.get_space_count(the_grid=the_grid)

        # if X has won
        if has_player_won('X'):
            return 'X'

        # else if O has won
        if has_player_won('O'):
            return 'O'

        # else if there are no empty spaces
        if space_count is 0:
            return 'D'

        # else game must be incomplete
        return 'U'

    @staticmethod
    # returns a copy of the grid passed
    def copy_grid(old_grid):

        # will hold exact contents of old_grid
        new_grid = []

        # for each row
        for i in range(len(old_grid)):

            # holds content of each row
            new_row = []

            # add content of row to new_row
            for j in range(len(old_grid[i])):
                new_row.append(old_grid[i][j])

            # add new_row to to new_grid
            new_grid.append(new_row)

        # return created copy
        return new_grid

    @staticmethod
    # returns a list of all possible legal moves
    def get_possibles_grids(the_grid, will_play):

        # will hold all newly created grids
        grid_list = []

        # for each location on the grid
        for i in range(len(the_grid)):
            for j in range(len(the_grid[i])):

                # if the grid is empty at this location
                if the_grid[i][j] is ' ':

                    # create copy of the grid with this location filled by the player
                    grid_copy = TicTac.copy_grid(old_grid=the_grid)
                    grid_copy[i][j] = will_play

                    # add the newly created grid to the move_list
                    grid_list.append(grid_copy)

        return grid_list

    @staticmethod
    # returns a random grid configuration (not necessarily a legal board)
    def get_random_grid():

        # returns a random possible board value 'X', 'O', or ' '
        def get_random_play():

            # generates random number
            rand_num = random.randint(0, 3)

            # returns player corresponding to rand_num
            if rand_num is 0:
                return 'X'
            if rand_num is 1:
                return 'O'
            else:
                return ' '

        # empty 2 x 2 grid will be filled randomly
        new_grid = [[], [], []]

        # for each space in the grid, assign a random player (or space)
        for row in new_grid:
            for i in range(3):
                row.append(get_random_play())

        return new_grid

    @staticmethod
    # returns an empty grid
    def get_empty_grid():

        # empty grid created with rows, but no columns
        new_grid = [[], [], []]

        # insert 3 separate spaces into each row
        for row in new_grid:
            for i in range(3):
                row.append(' ')

        return new_grid

    @staticmethod
    # returns the index of either the largest or smallest number in the list
    # if there are multiple candidates, returns he index of a random one
    def get_outstanding(the_list, go_for_max):

        # initially best candidate assumed to be first
        best_index = 0

        # will hold all of the max/min numbers
        best_candidates = []

        # for each item in the list
        for i in range(len(the_list)):

            # if maximum is desired, and this item is greater, replace best_index
            if go_for_max:
                if the_list[i] > the_list[best_index]:
                    best_index = i

            # if minimum is desired, and this item is less, replace best_index
            elif not go_for_max:
                if the_list[i] < the_list[best_index]:
                    best_index = i

        # for each item in the list
        for i in range(len(the_list)):

            # if the item is a min/max, add its index to best_candidates
            if the_list[i] == the_list[best_index]:
                best_candidates.append(i)

        # return a random member of best_candidates
        return best_candidates[random.randint(0, len(best_candidates) - 1)]

    @staticmethod
    # returns the opposing player to the one passed
    def get_opponent(player):

        # X or O handled
        if player == 'X':
            return 'O'
        if player == 'O':
            return 'X'

        # anything else handled
        else:
            print("Unfamiliar player passed to get_opponent: " + player)
            return None

    @staticmethod
    # returns the constant score associated with the player passed
    def get_score_const(the_player):

        # X, O, and Draw all handled
        if the_player is 'X':
            return TicTac.MAX_SCORE
        if the_player is 'O':
            return TicTac.MIN_SCORE
        if the_player is 'D':
            return TicTac.DRAW_SCORE

        else:
            print("Unfamiliar player passed to get_score_const: " + the_player)
            return None

    @staticmethod
    # returns whether or not the location on the grid is playable
    def is_loc_open(the_grid, location):

        # handle None location
        if location is None:
            return False

        # if the grid is empty at this location
        if the_grid[location[0]][location[1]] == ' ':
            return True

        # else space is not open
        return False

    # recursively calls score_grid to evaluate the score of the grid passed
    # dictionary function still untested
    def score_grid(self, the_grid, opponent):

        # opposite of opponent
        move_chooser = TicTac.get_opponent(player=opponent)

        # maximize if the choosing player is X
        to_maximize = move_chooser is 'X'

        # game state saved
        game_state = TicTac.get_game_state(the_grid=the_grid)

        # evaluate and return state of board if finished
        if game_state is not 'U':
            return TicTac.get_score_const(game_state)

        # move converted to string form
        move_string = TicTac.move_to_string(the_grid=the_grid, the_player=opponent)

        # if move_string is already in dictionary, return its score
        if move_string in self.known_scores:
            return self.known_scores[move_string]

        # list of possible responses to the grid passed
        grid_options = TicTac.get_possibles_grids(the_grid=the_grid, will_play=move_chooser)

        # list of scores corresponding to the grids in grid_options
        score_list = []

        for current_grid in grid_options:

            # convert this move into string form
            this_move_string = TicTac.move_to_string(the_grid=current_grid, the_player=move_chooser)

            # score of the current grid
            this_grid_score = self.score_grid(the_grid=current_grid, opponent=move_chooser)

            # add this_move_string to the dictionary with its score
            self.known_scores[this_move_string] = this_grid_score

            # add new score to the score list
            score_list.append(this_grid_score)

        # store the index of the best move
        best_index = TicTac.get_outstanding(the_list=score_list, go_for_max=to_maximize)

        # return the best score found - 1 to account for depth
        return score_list[best_index] - 1

    # chooses the best counter grid to the grid passed having just been played by opponent
    # uses score_grid
    def choose_move(self, the_grid, opponent):

        # opposite of opponent
        move_chooser = TicTac.get_opponent(player=opponent)

        to_maximize = move_chooser is 'X'

        # generate list of grid options
        grid_options = TicTac.get_possibles_grids(the_grid=the_grid, will_play=move_chooser)

        # catch immediate winning option
        for grid in grid_options:
            if TicTac.get_game_state(grid) == move_chooser:
                return grid

        # scores of potential grids
        score_list = []

        for current_grid in grid_options:

            # get score of this grid
            current_score = self.score_grid(the_grid=current_grid, opponent=move_chooser)

            # add calculated score to score_list
            score_list.append(current_score)

        # index of the best move
        best_index = TicTac.get_outstanding(the_list=score_list, go_for_max=to_maximize)

        # return the best grid
        return grid_options[best_index]

    # two CPU players are pit against each other
    # both use choose_move to select moves
    def CPUvsCPU(self):

        # game grid is initially empty
        game_grid = TicTac.get_empty_grid()

        # game state initially unfinished
        game_state = 'U'

        # first player is X
        former_player = 'O'

        # while game is unfinished
        while game_state is 'U':

            # current makes move
            game_grid = self.choose_move(the_grid=game_grid, opponent=former_player)

            # adjust game state in light of new move
            game_state = TicTac.get_game_state(game_grid)

            # switch players
            former_player = TicTac.get_opponent(former_player)

        # return outcome of game
        return game_state


pygame.init()
pygame.font.init()

# color definitions
white = (255, 255, 255)
black = (0, 0, 0)
red = (255, 0, 0)
green = (0, 255, 0)
blue = (0, 0, 255)

# player color preferences
color_pref = {'X': red, 'O': blue, ' ': white}

# game window dimensions and title
gameDisplay = pygame.display.set_mode((800, 600))
pygame.display.set_caption("Slippery")

# board settings
board_center = (400, 300)
square_width = 160
line_width = 30

# font information. Size is chosen based on grid square widths
my_font = pygame.font.SysFont(None, square_width)


# display letter in the space provided
def letter_in_space(the_letter, the_color, the_space):

    # where the letter will go
    letter_pos = (the_space[0] + square_width / 4, the_space[1] + square_width / 4)

    # display letter at the location desired
    screen_text = my_font.render(the_letter, True, the_color)
    gameDisplay.blit(screen_text, letter_pos)


# generates a grid with entries concerning the dimensions of their respective squares [x_cor, y_cor, width, length]
def get_space_grid():

    # each of the three lists in space grid represents a row of the board
    the_space_grid = [[], [], []]

    # column X coordinates
    column_0_x = board_center[0] - square_width - square_width / 2 - line_width
    column_1_x = board_center[0] - square_width / 2
    column_2_x = board_center[0] + square_width / 2 + line_width

    # row Y coordinates
    row_0_y = board_center[1] - square_width - square_width / 2 - line_width
    row_1_y = board_center[1] - square_width / 2
    row_2_y = board_center[1] + square_width / 2 + line_width

    # fill first row
    the_space_grid[0].append([column_0_x, row_0_y, square_width, square_width])
    the_space_grid[0].append([column_1_x, row_0_y, square_width, square_width])
    the_space_grid[0].append([column_2_x, row_0_y, square_width, square_width])

    # fill second row
    the_space_grid[1].append([column_0_x, row_1_y, square_width, square_width])
    the_space_grid[1].append([column_1_x, row_1_y, square_width, square_width])
    the_space_grid[1].append([column_2_x, row_1_y, square_width, square_width])

    # fill third row
    the_space_grid[2].append([column_0_x, row_2_y, square_width, square_width])
    the_space_grid[2].append([column_1_x, row_2_y, square_width, square_width])
    the_space_grid[2].append([column_2_x, row_2_y, square_width, square_width])

    return the_space_grid


# draws the grid with the provided board settings
def draw_grid(the_space_grid, the_char_grid):

    # background square side length
    background_side_length = square_width * 3 + line_width * 2

    # draw background (only grid lines will show)
    pygame.draw.rect(gameDisplay, black, [board_center[0] - background_side_length / 2, board_center[1] - background_side_length / 2, background_side_length, background_side_length])

    # for each space in the grid
    for i in range(len(the_space_grid)):
        for j in range(len(the_space_grid[i])):

            # current letter
            this_letter = the_char_grid[i][j]

            # draw a white box around this space
            pygame.draw.rect(gameDisplay, white, the_space_grid[i][j])

            # draw corresponding letter from the_char_grid in box
            letter_in_space(this_letter, color_pref[this_letter], the_space_grid[i][j])


# returns the the column and row of the square at the location passed
def get_space_for(the_space_grid, location):

    # returns whether or not the location is in the space passed
    def is_in_space(space):

        # if the location is not within the x bounds of the space
        if location[0] < space[0] or location[0] > (space[0] + square_width):
            return False

        # if the location is not within the y bounds of the space
        if location[1] < space[1] or location[1] > (space[1] + square_width):
            return False

        # else location must be in the space's boundaries
        return True

    # for each space in the space grid
    for i in range(len(the_space_grid)):
        for j in range(len(the_space_grid[i])):

            # if the space contains the loction, return return the space's (row, column)
            if is_in_space(the_space_grid[i][j]):
                return i, j

    # else location passed is not in any of the spaces
    return None


# runs through game, exits when someone is beat or draw occurs
# returns a char representing the outcome of the game
def main_loop():

    # create space_grid from dimension settings, my_tic TicTac object, and initially empty char_grid
    space_grid = get_space_grid()
    my_tic = TicTac()
    char_grid = my_tic.get_empty_grid()

    # game state is initially unfinished
    current_game_state = 'U'

    # user is temporarily forced to be X
    USER_player = 'X'
    # CPU_player = my_tic.get_opponent(USER_player)

    # currently user starts game
    user_turn = True

    # run until game is over
    while True:

        # user_play_loc reset
        user_play_loc = None

        # analyze all possible events
        for event in pygame.event.get():

            # if user pushes exit, shutdown game
            if event.type == pygame.QUIT:
                pygame.quit()
                quit()

            # if user mouses down
            if event.type == pygame.MOUSEBUTTONDOWN:

                # space mouse is currently in
                mouse_space_loc = get_space_for(space_grid, pygame.mouse.get_pos())

                # if the desired space is a legal place to play, assign it to user_play_loc
                if my_tic.is_loc_open(char_grid, mouse_space_loc):
                    user_play_loc = mouse_space_loc

                # else user_play_loc becomes None indicating no legal move was given
                else:
                    user_play_loc = None

        # if game is finished, return result
        if current_game_state != 'U':
            return current_game_state

        # if its the user's turn
        if user_turn:

            # if the user has played a legal move
            if user_play_loc is not None:

                # insert user play into grid
                char_grid[user_play_loc[0]][user_play_loc[1]] = USER_player

                # It is now CPU's turn
                user_turn = False

                # update game_state
                current_game_state = my_tic.get_game_state(char_grid)

        # else it is CPU's turn
        else:

            # opponent makes their move
            char_grid = my_tic.choose_move(char_grid, USER_player)

            # it is now USER's turn
            user_turn = True

            # update game_state
            current_game_state = my_tic.get_game_state(char_grid)

        # fill background and draw game grid
        gameDisplay.fill(white)
        draw_grid(space_grid, char_grid)
        pygame.display.update()


main_loop()

# shut down game after main_loop finishes (game over)
pygame.quit()
quit()
