"""
    Dijkstra's Algorithm
    Computes the shortest path from a start vertex to any other vertex in the graph
    Speed of computing the shortest path to a specific vertex is roughly the same as computing the shortest path to all vertices
    Could be sped up using a heap
"""

import heapq
import time

real_deal = "DData.txt"
new_example = "Fiver.txt"


# min heap can have items pushed and popped
class Heap:

    # initializes list that will serve as heap
    def __init__(self):

        self.the_list = []

    # returns and deletes the smallest member in the heap
    def pop(self):

        # if pop is attempted on empty list, return None
        if len(self.the_list) == 0:
            return None

        return heapq.heappop(self.the_list)

    # adds the item passed to the heap
    def push(self, item):

        heapq.heappush(self.the_list, item)


# member of a graph containing a list of all outgoing vertices
class Vertex:

    # empty outgoing vertex list created
    def __init__(self):
        self.out_verts = []


# directed graph containing edge weights
class Graph:

    # contains list of vertex references and
    def __init__(self):

        # list of all vertices in graph
        self.vert_list = []

        # dictionary will hold vertices represented by letter keys
        self.vert_dict = dict()

        # dictionary containing edge weights
        self.edge_weight_dict = dict()

    # returns the weight of the edge passed
    def get_weight(self, from_vert, to_vert):

        # if the edge passed is not in the the graph
        if (from_vert, to_vert) not in self.edge_weight_dict:
            return None

        return self.edge_weight_dict[(from_vert, to_vert)]

    # prints the adjacency list of each vertex in the graph
    def print_graph(self):

        for vert in self.vert_list:

            this_vertex = self.vert_dict[vert]

            print str(vert) + ":",

            print str(this_vertex.out_verts)


# contains static methods assisting the reading and transforming of graphs
class GraphHelp:

    # returns a graph read from the file passed
    @staticmethod
    def graph_from_file(file_name):

        the_graph = Graph()

        # file reading object
        the_file = open(file_name, 'r')

        for line in the_file:

            # split line by spaces
            char_list = line.split()

            # vertex being referenced in this line
            this_vert = int(char_list.pop(0))

            # if this vertex has not been seen yet, add it to the vertex list and vertex dictionary
            if this_vert not in the_graph.vert_list:
                the_graph.vert_list.append(this_vert)
                the_graph.vert_dict[this_vert] = Vertex()

            # isolate each pair
            for char in char_list:

                # pair contains outgoing edge destination and edge weight
                this_pair = char.split(',')

                # extract data from each pair
                this_edge_dest = int(this_pair[0])
                this_weight = int(this_pair[1])

                # if this edge destination has not been seen yet, add it to the vertex list and vertex dictionary
                if this_edge_dest not in the_graph.vert_list:
                    # the_graph.vert_list.append(this_edge_dest)
                    the_graph.vert_dict[this_edge_dest] = Vertex()

                # add outgoing edge destination to this vertex's adjacency list
                the_graph.vert_dict[this_vert].out_verts.append(this_edge_dest)

                # add the corresponding edge's weight to the weight dictionary
                the_graph.edge_weight_dict[(this_vert, this_edge_dest)] = this_weight

        return the_graph

    # returns a dictionary of shortest paths from the starting vertex to all other vertices
    @staticmethod
    def get_short_paths(the_graph, start_vert_id):

        # vertex keys have their shortest path distance from the start vertex stored
        shortest_path_to_length = dict()

        # vertex keys have their shortest path stored in the form of a list of edges
        shortest_path_to = dict()

        # X and V correspond to checked vertices and unchecked vertices accordingly
        checked_vertices = set()
        all_vertices = set(the_graph.vert_list)

        # information about starting vertex stored
        shortest_path_to_length[start_vert_id] = 0
        shortest_path_to[start_vert_id] = []

        checked_vertices.add(start_vert_id)
        all_vertices.discard(start_vert_id)

        # returns a list of all edges crossing that cross from X to X - V
        def get_crossing_edges():

            the_edge_list = []

            # every vertex in the graph
            for vert in the_graph.vert_list:

                # isolate vertex referenced in graph's vertex list
                this_vertex = the_graph.vert_dict[vert]

                # each vertex adjacent to this
                for out_vert in this_vertex.out_verts:

                    # if the tail of this edge is in the set and the head is not, add it to the edge list
                    if vert in checked_vertices and out_vert not in checked_vertices:
                        the_edge_list.append((vert, out_vert))

            return the_edge_list

        # greedily determines the best edge to seek
        def get_best_edge(edge_list):

            # most promising edge is initially assumed to be the first
            best_edge = edge_list[0]
            best_path_length = shortest_path_to_length[best_edge[0]] + the_graph.edge_weight_dict[best_edge]

            for edge in edge_list:

                # calculate the length of the path to the tail vertex of this edge
                this_path_length = shortest_path_to_length[edge[0]] + the_graph.edge_weight_dict[edge]

                # if this path is shorter than the best found, it becomes the new best
                if this_path_length < best_path_length:
                    best_edge = edge
                    best_path_length = this_path_length

            return best_edge

        # while there are still unchecked vertices
        while len(all_vertices - checked_vertices) > 0:

            the_best_edge = get_best_edge(get_crossing_edges())

            # extract edge data
            edge_tail = the_best_edge[0]
            edge_head = the_best_edge[1]

            # store edge head's path length
            shortest_path_to_length[edge_head] = shortest_path_to_length[edge_tail] + the_graph.edge_weight_dict[(edge_tail, edge_head)]

            # store shortest path to edge head
            shortest_path_to[edge_head] = shortest_path_to[edge_tail] + [the_best_edge]

            # add edge head to set of vertices checked
            checked_vertices.add(edge_head)

        # return dictionary of paths for each vertex
        return shortest_path_to

    # returns the path length of the path passed
    @staticmethod
    def get_path_length(the_graph, the_path):

        if len(the_path) == 0:
            return None

        total_path_length = 0

        for edge in the_path:
            total_path_length += the_graph.edge_weight_dict[edge]

        return total_path_length


# returns a list of the shortest paths to each of the vertices in the list passed
def run_through(file_name, vert_list, start_vert_id):

    my_graph = GraphHelp.graph_from_file(file_name)

    path_dict = GraphHelp.get_short_paths(my_graph, start_vert_id)

    result_list = []

    for vert in vert_list:
        result_list.append(GraphHelp.get_path_length(my_graph, path_dict[vert]))

    return result_list

my_vert_list = [7, 37, 59, 82, 99, 115, 133, 165, 188, 197]

start = time.time()

print run_through(real_deal, my_vert_list, 1)

print time.time() - start
