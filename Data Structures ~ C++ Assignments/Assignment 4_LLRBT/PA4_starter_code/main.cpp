#include "TestUtils.h"
#include "SpaceSectorBST.h"
#include "SpaceSectorLLRBT.h"
#include "kNN_DAT_Parser.h"
#include "kNN.h"
#include "KD_Tree.h"
#include "KDT_Node.h"
#include "kNN_Data.h"

#define NUMBER_TESTS 50

string TESTS_PATH = "testsIO/inputs/";
string EXPECTED_INIT_BST_PATH = "testsIO/expected_bst_init/";
string EXPECTED_INIT_LLRBT_PATH = "testsIO/expected_llrbt_init/";
string NODES_TO_DELETE_BST_PATH = "testsIO/nodes_to_delete/";
string EXPECTED_DELETE_BST_PATH = "testsIO/expected_bst_delete/";
string EXPECTED_TRAVERSALS_BST_PATH = "testsIO/expected_bst_traversals/";

string NODES_TO_FIND_BST_PATH = "testsIO/nodes_to_find_bst/";
string NODES_TO_FIND_LLRBT_PATH = "testsIO/nodes_to_find_llrbt/";

string EXPECTED_STELLAR_PATH_BST_PATH = "testsIO/expected_bst_path/";
string EXPECTED_STELLAR_PATH_LLRBT_PATH = "testsIO/expected_llrbt_path/";

string EXPECTED_OUTPUT_PATH = "testsIO/expected_output_path/";
string NODES_TO_FIND_BST_OUTPUT_PATH = "testsIO/nodes_to_find_bst_output/";
string NODES_TO_FIND_LLRBT_OUTPUT_PATH = "testsIO/nodes_to_find_llrbt_output/";

string BONUS_DATA_SIMPLE_FILE = "testsIO/simple_planet_train.dat";
string BONUS_DATA_SIMPLE_FILE_EXPECTED = "testsIO/simple_planet_train_expected.json";

string BONUS_DATA_COMPLEX_FILE = "testsIO/synthetic_planet_dataset_complex.dat";
string BONUS_DATA_COMPLEX_FILE_EXPECTED = "testsIO/synthetic_planet_dataset_complex_expected.json";



double test_init_bst() {
    double score = 0;
    for (int i = 1; i <= NUMBER_TESTS; i++) {
        SpaceSectorBST sector_map;
        string test_file_name = TESTS_PATH + "sectors_" + std::to_string(i) + ".dat";
        sector_map.readSectorsFromFile(test_file_name);
        json bst = serializeBST(sector_map.root);
        Sector* expected_root = deserializeBST(EXPECTED_INIT_BST_PATH + "expected_" + std::to_string(i) + ".json");
        json expected_bst = serializeBST(expected_root);
        if (expected_bst == bst) {
            score += 1;
        }
        delete_tree(expected_root);
    }

    return score / NUMBER_TESTS;
}



double test_delete_bst() {
    double score = 0;
    for (int i = 1; i <= NUMBER_TESTS; i++) {
        string test_file_name = TESTS_PATH + "sectors_" + std::to_string(i) + ".dat";
        SpaceSectorBST sector_map;
        sector_map.readSectorsFromFile(test_file_name);
        std::vector<std::string> selected_nodes = read_string_vector_from_file(
                NODES_TO_DELETE_BST_PATH + "nodes_" + std::to_string(i) + ".txt");
        for (int j = 0; j < selected_nodes.size(); j++) {
            sector_map.deleteSector(selected_nodes.at(j));
        }

        Sector* expected_root = deserializeBST(EXPECTED_DELETE_BST_PATH + "expected_" + std::to_string(i) + ".json");
        json expected_bst = serializeBST(expected_root);

        json bst = serializeBST(sector_map.root);

        if (expected_bst == bst) {
            score += 1;
        }
        delete_tree(expected_root);
    }
    return score / NUMBER_TESTS;
}

double grade_output_file(const string& expected_file_name, function<void()> student_code) {
    double score = 0.0;
    int total_number;

    // Read expected
    ifstream t(expected_file_name);
    if (!t.is_open()) {
        cerr << "Failed to open " << expected_file_name << endl;
        return 0.0; // Or handle the error as needed
    }
    stringstream buffer;
    buffer << t.rdbuf();
    string expected_output = buffer.str();

    vector<string> expected_output_split = split_trim(expected_output, "\n");
    total_number = expected_output_split.size();


    cout.flush();
    // Capture out
    ostringstream output_cout;
    streambuf* old_stdout_buf = cout.rdbuf(output_cout.rdbuf());

    student_code();

    // Enable out
    cout.rdbuf(old_stdout_buf);

    string captured_output = output_cout.str();
    vector<string> captured_output_split = split_trim(captured_output, "\n");

    for (int i = 0; i < captured_output_split.size(); i++) {
        if (i < total_number && captured_output_split[i] == expected_output_split[i]) {
            score += 1;
        }
    }

    return (total_number > 0) ? score / total_number : 0;
}

double test_bst_traversals() {
    double score = 0;
    for (int i = 1; i <= NUMBER_TESTS; i++) {
        string test_file_name = TESTS_PATH + "sectors_" + std::to_string(i) + ".dat";
        SpaceSectorBST sector_map;
        sector_map.readSectorsFromFile(test_file_name);


        // InOrder Traversal
        score += grade_output_file(EXPECTED_TRAVERSALS_BST_PATH + "expected_inorder_" + std::to_string(i) + ".txt", std::bind(&SpaceSectorBST::displaySectorsInOrder, &sector_map));

        // PreOrder Traversal
        score += grade_output_file(EXPECTED_TRAVERSALS_BST_PATH + "expected_preorder_" + std::to_string(i) + ".txt", std::bind(&SpaceSectorBST::displaySectorsPreOrder, &sector_map));

        // PostOrder Traversal
        score += grade_output_file(EXPECTED_TRAVERSALS_BST_PATH + "expected_postorder_" + std::to_string(i) + ".txt", std::bind(&SpaceSectorBST::displaySectorsPostOrder, &sector_map));
    }
    return score / (NUMBER_TESTS * 3);
}

double test_bst_pathfinding() {
    double score = 0;
    int k = 0;
    for (int i = 1; i <= NUMBER_TESTS; i++) {
        string test_file_name = TESTS_PATH + "sectors_" + std::to_string(i) + ".dat";
        SpaceSectorBST sector_map;
        sector_map.readSectorsFromFile(test_file_name);
        std::vector<std::string> selected_nodes = read_string_vector_from_file( NODES_TO_FIND_BST_PATH + "nodes_" + std::to_string(i) + ".txt");

        for (int j = 0; j < selected_nodes.size(); j++) {
            // Find path
            vector<Sector*> path = sector_map.getStellarPath(selected_nodes.at(j));
            vector<Sector*> expected_path = load_path_bst(EXPECTED_STELLAR_PATH_BST_PATH + "path_" + to_string(k++) + ".json");

            json path_json = serializePathBST(path);
            json expected_json = serializePathBST(expected_path);

            if (expected_json == path_json) {
                score += 1;
            }

            for (int z = 0; z < expected_path.size(); z++) {
                delete expected_path[z];
            }

        }
    }
    return score / k;
}


double test_init_llrbt() {
    double score = 0;
    for (int i = 1; i <= NUMBER_TESTS; i++) {
        SpaceSectorLLRBT sector_map;
        string test_file_name = TESTS_PATH + "sectors_" + std::to_string(i) + ".dat";
        sector_map.readSectorsFromFile(test_file_name);
        json bst = serializeBST(sector_map.root);
        Sector* expected_root = deserializeLLRBT(EXPECTED_INIT_LLRBT_PATH + "expected_" + std::to_string(i) + ".json");
        json expected_bst = serializeBST(expected_root);
        if (expected_bst == bst) {
            score += 1;
        }
        delete_tree(expected_root);
    }
    return score / NUMBER_TESTS;
}

double test_llrbt_pathfinding() {
    double score = 0;
    int k = 0;
    for (int i = 1; i <= NUMBER_TESTS; i++) {
        string test_file_name = TESTS_PATH + "sectors_" + std::to_string(i) + ".dat";
        SpaceSectorLLRBT sector_map;
        sector_map.readSectorsFromFile(test_file_name);
        std::vector<std::string> selected_nodes = read_string_vector_from_file( NODES_TO_FIND_LLRBT_PATH + "nodes_" + std::to_string(i) + ".txt");

        for (int j = 0; j < selected_nodes.size(); j++) {
            // Find path
            vector<Sector*> path = sector_map.getStellarPath(selected_nodes.at(j));
            vector<Sector*> expected_path = load_path_llrbt(EXPECTED_STELLAR_PATH_LLRBT_PATH + "path_" + to_string(k++) + ".json");

            json path_json = serializePathLLRBT(path);
            json expected_json = serializePathLLRBT(expected_path);

            if (expected_json == path_json) {
                score += 1;
            }

            for (int z = 0; z < expected_path.size(); z++) {
                delete expected_path[z];
            }

        }
    }
    return score / k;
}

void run_sample_main(int i) {
    string test_file_name = TESTS_PATH + "sectors_" + std::to_string(i) + ".dat";
    //************ Task 1: Space Sector Mapping ***********//
    SpaceSectorBST sector_map_BST;
    sector_map_BST.readSectorsFromFile(test_file_name);
    sector_map_BST.displaySectorsInOrder();
    sector_map_BST.displaySectorsPreOrder();
    sector_map_BST.displaySectorsPostOrder();

    std::vector<std::string> selected_nodes = read_string_vector_from_file(NODES_TO_FIND_BST_OUTPUT_PATH + "nodes_" + to_string(i) + ".txt");

    for (int j = 0; j < selected_nodes.size(); j++) {
        std::vector<Sector*> stellar_path = sector_map_BST.getStellarPath(selected_nodes.at(j));
        sector_map_BST.printStellarPath(stellar_path);

        cout << endl;
    }

    //************ Task 2: Optimizing Space Sector Mapping ***********//
    SpaceSectorLLRBT sector_map_LLRBT;
    sector_map_LLRBT.readSectorsFromFile(test_file_name);
    sector_map_LLRBT.displaySectorsInOrder();
    sector_map_LLRBT.displaySectorsPreOrder();
    sector_map_LLRBT.displaySectorsPostOrder();

    std::vector<std::string> selected_nodes_llrbt = read_string_vector_from_file(NODES_TO_FIND_LLRBT_OUTPUT_PATH + "nodes_" + to_string(i) + ".txt");

    for (int j = 0; j < selected_nodes_llrbt.size(); j++) {
        std::vector<Sector*> stellar_path_2 = sector_map_LLRBT.getStellarPath(selected_nodes_llrbt.at(j));
        sector_map_LLRBT.printStellarPath(stellar_path_2);
        cout << endl;
    }

}

void test_output() {
    double score = 0;
    for (int i = 1; i <= NUMBER_TESTS; i++) {
        double current_score = grade_output_file(EXPECTED_OUTPUT_PATH + "expected_output_" + std::to_string(i) + ".txt", [i] () {
            run_sample_main(i);
        });

        score += current_score;
        if (current_score != 1) {
            cout << current_score << endl;
            cout << i << endl;
        };
    }
    score /= NUMBER_TESTS;
    cout << fixed << setprecision(6) << score;
}

double test_bonus_tree_construct() {
    double score = 0;
    string dataFileNameSimple = BONUS_DATA_SIMPLE_FILE;
    kNN_Dat_Parser parser;
    Dataset data = parser.parse(dataFileNameSimple);
    int k = 3;
    KNN knnClassifier(k, data.threshold);
    knnClassifier.train(data);
    KD_Tree tree = knnClassifier.tree;
    KDTreeNode * rootSimple = tree.getRoot();

    string dataFileNameComplex = BONUS_DATA_COMPLEX_FILE;
    Dataset dataComplex = parser.parse(dataFileNameComplex);
    KNN knnClassifierComplex(k, dataComplex.threshold);
    knnClassifierComplex.train(dataComplex);
    KD_Tree treeComplex = knnClassifierComplex.tree;
    KDTreeNode * rootComplex = treeComplex.getRoot();

    KDTreeNode * rootSimpleExpected = load_root_kdtree(BONUS_DATA_SIMPLE_FILE_EXPECTED);
    KDTreeNode * rootComplexExpected = load_root_kdtree(BONUS_DATA_COMPLEX_FILE_EXPECTED);
    
    json kdTreeSimple = serializeKDTree(rootSimple);
    json kdTreeSimpleExpected  = serializeKDTree(rootSimpleExpected);

    if (kdTreeSimple == kdTreeSimpleExpected) {
        score += 1;
    }

    json kdTreeComplex = serializeKDTree(rootComplex);
    json kdTreeComplexExpected  = serializeKDTree(rootComplexExpected);

    if (kdTreeComplex == kdTreeComplexExpected) {
        score += 1;
    }


    delete rootSimpleExpected;
    delete rootComplexExpected;
    
    return score / 2.0;
}

double test_bonus_predict() {
    
    double score = 0;

    if (test_bonus_tree_construct() != 1.0) {
        return score;
    }

    string dataFileNameSimple = BONUS_DATA_SIMPLE_FILE;
    kNN_Dat_Parser parser;
    Dataset data = parser.parse(dataFileNameSimple);
    int k = 3;
    KNN knnClassifier(k, data.threshold);
    knnClassifier.train(data);
    KD_Tree tree = knnClassifier.tree;
    KDTreeNode * rootSimple = tree.getRoot();

     // test instance
    std::vector<double> testFeatures = {1.3232202558909871, 3.172770177200133, 18.57605415623152, 0.22339666018454057, 81.15184706218832, 695.926406517461, 0.9139624956958795};
    Point testPoint(testFeatures, "");

    // predict the label
    int predictedLabelSimple = knnClassifier.predict(testPoint);
    if (predictedLabelSimple == 0) {
        score += 1;
    }

    
    string dataFileNameComplex = BONUS_DATA_COMPLEX_FILE;
    Dataset dataComplex = parser.parse(dataFileNameComplex);
    KNN knnClassifierComplex(k, dataComplex.threshold);
    knnClassifierComplex.train(dataComplex);
    KD_Tree treeComplex = knnClassifierComplex.tree;
    KDTreeNode * rootComplex = treeComplex.getRoot();


    std::vector<double> testFeaturesComplex = {-71.6673304499796,  398.75431414049376,  -57.72014106100545,  64.15796357831525,  169.60713574138128,  -12.358080577786112,  205.3147161940572,  344.05997395091646,  303.89380101588677,  -42.01033795358779,  9,  393.0520588956815,  385.4060183813772,  387.07677281418887,  313.3570224115029,  0,  4};
    Point testPointComplex(testFeaturesComplex, "");

    // predict the label
    int predictedLabelComplex = knnClassifierComplex.predict(testPointComplex);

    if (predictedLabelComplex == 1) {
        score += 1;
    }

    return score / 2.0;
}

int main(int argc, char** argv) {
    
    cout << "test_init_bst: ";
    test_silent(test_init_bst);
    cout << endl;

    cout << "test_delete_bst: ";
    test_silent(test_delete_bst);
    cout << endl;

    cout << "test_bst_traversals: ";
    test_silent(test_bst_traversals);
    cout << endl;

    cout << "test_bst_pathfinding: ";
    test_silent(test_bst_pathfinding);
    cout << endl;

    cout << "test_init_llrbt: ";
    test_silent(test_init_llrbt);
    cout << endl;

    cout << "test_llrbt_pathfinding: ";
    test_silent(test_llrbt_pathfinding);
    cout << endl;

    cout << "test_output: ";
    test_output();
    cout << endl;

    cout << "test_bonus_tree_construct: ";
    test_silent(test_bonus_tree_construct);
    cout << endl;

    cout << "test_bonus_predict: ";
    test_silent(test_bonus_predict);
	cout << endl;

    return 0;
}