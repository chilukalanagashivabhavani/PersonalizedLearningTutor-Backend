package backend.config;

import backend.entity.Course;
import backend.entity.Lesson;
import backend.repository.CourseRepository;
import backend.repository.LessonRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LessonDataInitializer {

    @Bean
    CommandLineRunner createLessons(
            CourseRepository courseRepository,
            LessonRepository lessonRepository) {

        return args -> {

            /*
             * The old version created generic lessons such as
             * "Fundamentals" and "Core Concepts".
             *
             * We only recreate lessons when the database is empty.
             * This prevents the application from deleting student progress
             * every time the server starts.
             */

            if (lessonRepository.count() > 0) {
                return;
            }

            for (Course course : courseRepository.findAll()) {

                String title = course.getTitle();

                // =====================================================
                // MACHINE LEARNING
                // =====================================================

                if (title.equalsIgnoreCase("Machine Learning")) {

                    lessonRepository.save(new Lesson(
                        course,
                        "Introduction to Machine Learning",
                        """
                        Machine Learning (ML) is a branch of Artificial Intelligence
                        that allows computers to learn patterns from data and make
                        predictions or decisions without being explicitly programmed
                        for every situation.

                        Machine learning systems learn from examples. The quality and
                        quantity of data strongly affect the performance of the model.

                        Important terms:
                        • Dataset – collection of data used for learning.
                        • Feature – an input variable used by the model.
                        • Label – the expected output in supervised learning.
                        • Model – the learned mathematical representation.
                        • Training – the process of learning patterns from data.

                        Example:
                        A house-price prediction system can use features such as
                        area, number of bedrooms and location to predict the price.

                        Key Points:
                        • ML learns patterns from data.
                        • Features are inputs to the model.
                        • Models are trained using data.
                        • ML is used for prediction, classification and many other tasks.
                        """,
                        1
                    ));

                    lessonRepository.save(new Lesson(
                        course,
                        "Types of Machine Learning",
                        """
                        Machine Learning is commonly divided into three major types:

                        1. Supervised Learning
                        The model learns from labelled data where both input and
                        expected output are available.

                        Examples:
                        • Classification
                        • Regression

                        2. Unsupervised Learning
                        The model works with data without labelled outputs and tries
                        to discover hidden patterns or groups.

                        Examples:
                        • Clustering
                        • Dimensionality reduction

                        3. Reinforcement Learning
                        An agent learns by interacting with an environment.
                        It receives rewards for desirable actions and penalties
                        for undesirable actions.

                        Example:
                        A game-playing agent learns which actions produce better
                        rewards.

                        Key Point:
                        The major difference is how the learning system receives
                        information and feedback from its environment.
                        """,
                        2
                    ));

                    lessonRepository.save(new Lesson(
                        course,
                        "Regression and Classification",
                        """
                        Regression and classification are two important supervised
                        learning tasks.

                        Regression predicts a continuous numerical value.

                        Example:
                        Predicting the price of a house:
                        ₹40 lakh, ₹55 lakh, ₹72 lakh, etc.

                        Classification predicts a category or class.

                        Example:
                        An email can be classified as:
                        • Spam
                        • Not Spam

                        Common regression algorithms include:
                        • Linear Regression
                        • Decision Tree Regression
                        • Random Forest Regression

                        Common classification algorithms include:
                        • Logistic Regression
                        • Decision Trees
                        • Random Forest
                        • Support Vector Machines

                        Key Point:
                        Regression predicts numerical values, while classification
                        predicts categories.
                        """,
                        3
                    ));

                    lessonRepository.save(new Lesson(
                        course,
                        "Model Training and Evaluation",
                        """
                        A machine learning model must be trained and evaluated
                        carefully.

                        A dataset is commonly divided into:
                        • Training data – used to learn the model.
                        • Validation data – used to tune model settings.
                        • Test data – used to evaluate final performance.

                        Common evaluation measures include:

                        Regression:
                        • MAE – Mean Absolute Error
                        • MSE – Mean Squared Error
                        • RMSE – Root Mean Squared Error
                        • R² Score

                        Classification:
                        • Accuracy
                        • Precision
                        • Recall
                        • F1-score

                        A model should not simply memorize the training data.
                        It should perform well on new, unseen data.

                        This ability is called generalization.
                        """,
                        4
                    ));

                    lessonRepository.save(new Lesson(
                        course,
                        "Overfitting, Underfitting and Applications",
                        """
                        Overfitting occurs when a model learns the training data
                        too closely, including noise. It performs very well on
                        training data but poorly on new data.

                        Underfitting occurs when a model is too simple to learn
                        the important patterns in the data.

                        Ways to reduce overfitting include:
                        • Using more training data
                        • Regularization
                        • Cross-validation
                        • Reducing model complexity
                        • Early stopping for suitable models

                        Machine Learning applications include:
                        • Recommendation systems
                        • Fraud detection
                        • Medical prediction
                        • Image recognition
                        • Speech recognition
                        • Price prediction

                        Course Summary:
                        Machine Learning uses data to learn patterns and make
                        useful predictions or decisions.
                        """,
                        5
                    ));
                }

                // =====================================================
                // DEEP LEARNING
                // =====================================================

                else if (title.equalsIgnoreCase("Deep Learning")) {

                    lessonRepository.save(new Lesson(
                        course,
                        "Introduction to Deep Learning",
                        """
                        Deep Learning is a subfield of Machine Learning that uses
                        artificial neural networks with multiple layers.

                        A neural network contains:
                        • Input layer
                        • One or more hidden layers
                        • Output layer

                        Each neuron receives inputs, applies weights and produces
                        an output using an activation function.

                        Deep learning is especially useful for complex data such as:
                        • Images
                        • Audio
                        • Video
                        • Natural language

                        Unlike many traditional ML approaches, deep learning models
                        can automatically learn useful representations from raw data.

                        Example:
                        A deep neural network can learn features from an image
                        starting from simple patterns and progressing to complex
                        objects.
                        """,
                        1
                    ));

                    lessonRepository.save(new Lesson(
                        course,
                        "Artificial Neural Networks",
                        """
                        An Artificial Neural Network (ANN) is inspired by the
                        information-processing structure of biological neurons.

                        A neuron receives input values and combines them with
                        weights and a bias.

                        The result is passed through an activation function.

                        Common activation functions include:
                        • ReLU
                        • Sigmoid
                        • Tanh
                        • Softmax

                        ReLU is commonly used in hidden layers because it is simple
                        and helps neural networks learn non-linear relationships.

                        During training, the network adjusts its weights to reduce
                        the difference between predicted and expected outputs.
                        """,
                        2
                    ));

                    lessonRepository.save(new Lesson(
                        course,
                        "Convolutional Neural Networks",
                        """
                        A Convolutional Neural Network (CNN) is a deep learning
                        architecture designed especially for image and spatial data.

                        Important CNN components include:

                        1. Convolution layer
                        Extracts useful features such as edges and patterns.

                        2. Activation function
                        Usually introduces non-linearity using functions such as ReLU.

                        3. Pooling layer
                        Reduces spatial dimensions while retaining important
                        information.

                        4. Fully connected layer
                        Uses learned features for final prediction.

                        CNNs are widely used for:
                        • Image classification
                        • Object detection
                        • Face recognition
                        • Medical image analysis
                        """,
                        3
                    ));

                    lessonRepository.save(new Lesson(
                        course,
                        "RNN, LSTM and GRU",
                        """
                        Recurrent Neural Networks (RNNs) are designed for sequential
                        data where the order of information is important.

                        Examples:
                        • Text
                        • Speech
                        • Time-series data

                        RNNs maintain information from previous time steps using
                        a hidden state.

                        LSTM (Long Short-Term Memory) networks improve traditional
                        RNNs by using gates to control information flow.

                        Important LSTM gates:
                        • Forget gate
                        • Input gate
                        • Output gate

                        GRU (Gated Recurrent Unit) is another gated recurrent
                        architecture with a simpler structure than LSTM.

                        These architectures help deal with long-term dependencies
                        in sequential data.
                        """,
                        4
                    ));

                    lessonRepository.save(new Lesson(
                        course,
                        "Autoencoders and Generative Models",
                        """
                        An Autoencoder is a neural network that learns to represent
                        input data in a compressed form and reconstruct it.

                        It contains:
                        • Encoder
                        • Latent representation
                        • Decoder

                        Applications include:
                        • Dimensionality reduction
                        • Noise removal
                        • Feature learning

                        A Variational Autoencoder (VAE) learns a probability-based
                        latent representation and can generate new samples.

                        Generative Adversarial Networks (GANs) use two networks:
                        • Generator
                        • Discriminator

                        The generator creates samples while the discriminator tries
                        to distinguish generated samples from real samples.

                        Deep learning therefore supports both prediction and
                        generation of complex data.
                        """,
                        5
                    ));
                }

                // =====================================================
                // PYTHON PROGRAMMING
                // =====================================================

                else if (title.equalsIgnoreCase("Python Programming")) {

                    lessonRepository.save(new Lesson(
                        course,
                        "Introduction to Python",
                        """
                        Python is a high-level, interpreted programming language
                        known for its simple syntax and readability.

                        Python is widely used in:
                        • Web development
                        • Data science
                        • Artificial Intelligence
                        • Machine Learning
                        • Automation
                        • Software development

                        Example:

                        name = "Bhavani"
                        print(name)

                        Python does not require a separate declaration of the
                        variable type in basic programs.

                        Important features:
                        • Easy-to-read syntax
                        • Large standard library
                        • Large ecosystem of packages
                        • Supports object-oriented programming
                        """,
                        1
                    ));

                    lessonRepository.save(new Lesson(
                        course,
                        "Variables, Data Types and Operators",
                        """
                        Variables are used to store values.

                        Example:
                        age = 20
                        name = "Student"

                        Common Python data types include:
                        • int – integers
                        • float – decimal numbers
                        • str – text
                        • bool – True or False
                        • list – ordered collection
                        • tuple – immutable collection
                        • set – collection of unique values
                        • dict – key-value pairs

                        Common operators include:
                        • Arithmetic operators
                        • Comparison operators
                        • Logical operators
                        • Assignment operators

                        Understanding data types and operators is important
                        for writing correct Python programs.
                        """,
                        2
                    ));

                    lessonRepository.save(new Lesson(
                        course,
                        "Conditions and Loops",
                        """
                        Conditional statements allow a program to make decisions.

                        Python uses:
                        • if
                        • elif
                        • else

                        Example:

                        if marks >= 40:
                            print("Pass")
                        else:
                            print("Fail")

                        Loops are used to repeat a block of code.

                        Python provides:
                        • for loop
                        • while loop

                        The range() function is commonly used with for loops.

                        Loops and conditions form the basic control flow of
                        most programming applications.
                        """,
                        3
                    ));

                    lessonRepository.save(new Lesson(
                        course,
                        "Functions and Data Structures",
                        """
                        A function is a reusable block of code that performs
                        a specific task.

                        Example:

                        def add(a, b):
                            return a + b

                        Functions improve code reuse and organization.

                        Important Python data structures include:
                        • Lists
                        • Tuples
                        • Sets
                        • Dictionaries

                        Lists are mutable, while tuples are immutable.

                        Dictionaries store data using key-value pairs.

                        Choosing the correct data structure can make programs
                        easier to write and more efficient.
                        """,
                        4
                    ));

                    lessonRepository.save(new Lesson(
                        course,
                        "Object-Oriented Programming and Modules",
                        """
                        Python supports Object-Oriented Programming (OOP).

                        Important OOP concepts include:
                        • Class
                        • Object
                        • Encapsulation
                        • Inheritance
                        • Polymorphism

                        A class acts as a blueprint for creating objects.

                        Python also supports modules and packages, which allow
                        programmers to organize code into reusable components.

                        Example:
                        A machine learning project can separate data processing,
                        model training and prediction into different modules.

                        These concepts are important when building larger Python
                        applications.
                        """,
                        5
                    ));
                }

                // =====================================================
                // DATA STRUCTURES
                // =====================================================

                else if (title.equalsIgnoreCase("Data Structures")) {

                    lessonRepository.save(new Lesson(
                        course,
                        "Introduction to Data Structures",
                        """
                        A data structure is a way of organizing and storing data
                        so that it can be accessed and modified efficiently.

                        Data structures are important because different problems
                        require different ways of storing information.

                        Common data structures include:
                        • Arrays
                        • Linked Lists
                        • Stacks
                        • Queues
                        • Trees
                        • Graphs
                        • Hash tables

                        Data structures are closely connected with algorithms.

                        Choosing an appropriate data structure can significantly
                        improve the efficiency of a program.
                        """,
                        1
                    ));

                    lessonRepository.save(new Lesson(
                        course,
                        "Arrays and Linked Lists",
                        """
                        An array stores elements in a contiguous memory structure
                        and provides efficient access using an index.

                        Example:
                        [10, 20, 30, 40]

                        Array access by index is generally fast.

                        A linked list consists of nodes where each node contains
                        data and a link to another node.

                        Types of linked lists include:
                        • Singly linked list
                        • Doubly linked list
                        • Circular linked list

                        Arrays are useful when fast indexed access is important.
                        Linked lists are useful when frequent insertion and
                        deletion operations are required.
                        """,
                        2
                    ));

                    lessonRepository.save(new Lesson(
                        course,
                        "Stacks and Queues",
                        """
                        A stack follows the LIFO principle:
                        Last In, First Out.

                        Common stack operations:
                        • Push
                        • Pop
                        • Peek

                        Example:
                        A stack can be used for browser history or undo operations.

                        A queue follows the FIFO principle:
                        First In, First Out.

                        Common queue operations:
                        • Enqueue
                        • Dequeue

                        Queues are useful in:
                        • Scheduling
                        • Printer management
                        • Breadth-first search

                        Understanding LIFO and FIFO is essential for selecting
                        the correct data structure.
                        """,
                        3
                    ));

                    lessonRepository.save(new Lesson(
                        course,
                        "Trees and Binary Search Trees",
                        """
                        A tree is a hierarchical data structure consisting of nodes.

                        Important terms:
                        • Root
                        • Parent
                        • Child
                        • Leaf
                        • Height

                        A Binary Search Tree (BST) follows an ordering rule:
                        values smaller than a node are placed in its left subtree,
                        while larger values are placed in its right subtree.

                        Common tree traversals:
                        • Inorder
                        • Preorder
                        • Postorder

                        Inorder traversal of a valid BST produces values in
                        sorted order.
                        """,
                        4
                    ));

                    lessonRepository.save(new Lesson(
                        course,
                        "Graphs and Searching Algorithms",
                        """
                        A graph consists of vertices and edges.

                        Graphs can represent:
                        • Road networks
                        • Social networks
                        • Computer networks
                        • Relationships between objects

                        Two common graph traversal algorithms are:

                        Breadth-First Search (BFS)
                        Uses a queue and explores nodes level by level.

                        Depth-First Search (DFS)
                        Explores as far as possible along a branch before
                        backtracking.

                        Searching algorithms help us efficiently locate data
                        or explore relationships in a data structure.
                        """,
                        5
                    ));
                }

                // =====================================================
                // ARTIFICIAL INTELLIGENCE
                // =====================================================

                else if (title.equalsIgnoreCase("Artificial Intelligence")) {

                    lessonRepository.save(new Lesson(
                        course,
                        "Introduction to Artificial Intelligence",
                        """
                        Artificial Intelligence (AI) is the field of computing
                        concerned with creating systems that can perform tasks
                        that normally require human-like intelligence.

                        AI systems can perform tasks such as:
                        • Reasoning
                        • Learning
                        • Problem solving
                        • Perception
                        • Language understanding
                        • Decision making

                        Examples of AI applications include:
                        • Virtual assistants
                        • Recommendation systems
                        • Autonomous vehicles
                        • Medical decision-support systems
                        • Fraud detection

                        Machine Learning is one important approach used to build
                        intelligent systems.
                        """,
                        1
                    ));

                    lessonRepository.save(new Lesson(
                        course,
                        "Intelligent Agents",
                        """
                        An intelligent agent is a system that perceives its
                        environment and takes actions to achieve a goal.

                        An agent generally has:
                        • Sensors – receive information from the environment.
                        • Actuators – perform actions.

                        Example:
                        A self-driving car uses sensors to understand its
                        surroundings and actuators to control the vehicle.

                        An agent should select actions that help it achieve
                        its objectives.

                        The concept of agents is fundamental to understanding
                        how intelligent systems interact with their environment.
                        """,
                        2
                    ));

                    lessonRepository.save(new Lesson(
                        course,
                        "Problem Solving and Search",
                        """
                        AI often solves problems by searching through possible
                        states or solutions.

                        Important search methods include:

                        Uninformed search:
                        • Breadth-First Search
                        • Depth-First Search

                        Informed search:
                        • Greedy Best-First Search
                        • A* Search

                        A search problem can be described using:
                        • Initial state
                        • Actions
                        • Transition model
                        • Goal state
                        • Path cost

                        Search algorithms are widely used in navigation,
                        planning and game-playing systems.
                        """,
                        3
                    ));

                    lessonRepository.save(new Lesson(
                        course,
                        "Knowledge Representation and Reasoning",
                        """
                        Knowledge representation is the process of representing
                        information so that an AI system can reason about it.

                        Common approaches include:
                        • Logic
                        • Rules
                        • Semantic networks
                        • Knowledge graphs

                        Reasoning allows an AI system to derive new information
                        from existing knowledge.

                        Example:

                        Fact:
                        All humans are mortal.

                        Fact:
                        Ravi is a human.

                        Reasoning:
                        Ravi is mortal.

                        Knowledge representation is important for building
                        systems that can make logical decisions.
                        """,
                        4
                    ));

                    lessonRepository.save(new Lesson(
                        course,
                        "AI Applications and Responsible AI",
                        """
                        AI is used in many areas:

                        • Healthcare
                        • Education
                        • Finance
                        • Manufacturing
                        • Cybersecurity
                        • Transportation
                        • Entertainment

                        As AI becomes more powerful, responsible development
                        becomes important.

                        Important considerations include:
                        • Fairness
                        • Privacy
                        • Transparency
                        • Security
                        • Accountability

                        AI systems should be designed and evaluated carefully,
                        especially when their decisions affect people.

                        Course Summary:
                        AI combines methods such as search, reasoning and
                        learning to build systems capable of intelligent behavior.
                        """,
                        5
                    ));
                }
            }

            System.out.println("==============================================");
            System.out.println("25 topic-based lessons created successfully!");
            System.out.println("==============================================");
        };
    }
}