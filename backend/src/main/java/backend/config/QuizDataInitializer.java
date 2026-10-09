package backend.config;

import backend.entity.Course;
import backend.entity.Question;
import backend.entity.Quiz;
import backend.repository.CourseRepository;
import backend.repository.QuestionRepository;
import backend.repository.QuizRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuizDataInitializer {

    @Bean
    CommandLineRunner createQuizzes(
            CourseRepository courseRepository,
            QuizRepository quizRepository,
            QuestionRepository questionRepository) {

        return args -> {

            // Do not recreate quizzes every time the server starts.
            if (quizRepository.count() > 0) {
                return;
            }

            for (Course course : courseRepository.findAll()) {

                String title = course.getTitle();

                Quiz quiz = new Quiz(
                        course,
                        title + " Assessment",
                        5);

                quizRepository.save(quiz);

                createQuestions(
                        quiz,
                        title,
                        questionRepository);
            }

            System.out.println("==============================================");
            System.out.println("5 topic-based quizzes and 25 questions created!");
            System.out.println("==============================================");
        };
    }

    private void createQuestions(
            Quiz quiz,
            String courseTitle,
            QuestionRepository repository) {

        // =========================================================
        // MACHINE LEARNING
        // =========================================================

        if (courseTitle.equalsIgnoreCase("Machine Learning")) {

            // Topic 1: Introduction to Machine Learning
            repository.save(new Question(
                    quiz,
                    "What is the main idea of Machine Learning?",
                    "Learning patterns from data to make predictions or decisions",
                    "Manually programming every possible decision",
                    "Replacing computer hardware",
                    "Deleting unnecessary data",
                    "A"));

            // Topic 2: Types of Machine Learning
            repository.save(new Question(
                    quiz,
                    "Which type of Machine Learning learns from labelled data?",
                    "Unsupervised Learning",
                    "Supervised Learning",
                    "Reinforcement Learning",
                    "Random Learning",
                    "B"));

            // Topic 3: Regression and Classification
            repository.save(new Question(
                    quiz,
                    "Which Machine Learning task predicts a continuous numerical value?",
                    "Classification",
                    "Clustering",
                    "Regression",
                    "Association",
                    "C"));

            // Topic 4: Model Training and Evaluation
            repository.save(new Question(
                    quiz,
                    "Which metric is commonly used to evaluate a regression model?",
                    "MAE",
                    "Accuracy only",
                    "Precision only",
                    "Recall only",
                    "A"));

            // Topic 5: Overfitting
            repository.save(new Question(
                    quiz,
                    "Which technique can help reduce overfitting?",
                    "Increasing model complexity without limit",
                    "Regularization",
                    "Removing all training data",
                    "Avoiding validation",
                    "B"));
        }

        // =========================================================
        // DEEP LEARNING
        // =========================================================

        else if (courseTitle.equalsIgnoreCase("Deep Learning")) {

            // Topic 1: Introduction to Deep Learning
            repository.save(new Question(
                    quiz,
                    "What is Deep Learning mainly based on?",
                    "Artificial neural networks with multiple layers",
                    "Database tables",
                    "Operating system processes",
                    "Computer networking",
                    "A"));

            // Topic 2: Artificial Neural Networks
            repository.save(new Question(
                    quiz,
                    "Which activation function is commonly used in hidden layers?",
                    "SQL",
                    "ReLU",
                    "HTML",
                    "CSS",
                    "B"));

            // Topic 3: CNN
            repository.save(new Question(
                    quiz,
                    "What type of data are CNNs especially suitable for?",
                    "Only numerical tables",
                    "Only text files",
                    "Images and spatial data",
                    "Operating system files",
                    "C"));

            // Topic 4: RNN, LSTM and GRU
            repository.save(new Question(
                    quiz,
                    "Which LSTM gate controls information that should be removed?",
                    "Output gate",
                    "Input gate",
                    "Forget gate",
                    "Pooling gate",
                    "C"));

            // Topic 5: Autoencoders and Generative Models
            repository.save(new Question(
                    quiz,
                    "Which two networks form a GAN?",
                    "Encoder and Decoder",
                    "Generator and Discriminator",
                    "Input and Output",
                    "Trainer and Tester",
                    "B"));
        }

        // =========================================================
        // PYTHON PROGRAMMING
        // =========================================================

        else if (courseTitle.equalsIgnoreCase("Python Programming")) {

            // Topic 1: Introduction to Python
            repository.save(new Question(
                    quiz,
                    "Which statement about Python is correct?",
                    "Python is known for readable and simple syntax",
                    "Python can only be used for web development",
                    "Python does not support functions",
                    "Python cannot be used for AI",
                    "A"));

            // Topic 2: Variables and Data Types
            repository.save(new Question(
                    quiz,
                    "Which Python data type stores key-value pairs?",
                    "List",
                    "Tuple",
                    "Dictionary",
                    "Set",
                    "C"));

            // Topic 3: Conditions and Loops
            repository.save(new Question(
                    quiz,
                    "Which statement is used to make a decision in Python?",
                    "if",
                    "print",
                    "import",
                    "return",
                    "A"));

            // Topic 4: Functions and Data Structures
            repository.save(new Question(
                    quiz,
                    "What is the main purpose of a function?",
                    "To shut down the program",
                    "To create a reusable block of code",
                    "To delete variables",
                    "To remove data structures",
                    "B"));

            // Topic 5: OOP and Modules
            repository.save(new Question(
                    quiz,
                    "Which OOP concept allows a class to acquire features from another class?",
                    "Inheritance",
                    "Compilation",
                    "Iteration",
                    "Indexing",
                    "A"));
        }

        // =========================================================
        // DATA STRUCTURES
        // =========================================================

        else if (courseTitle.equalsIgnoreCase("Data Structures")) {

            // Topic 1: Introduction
            repository.save(new Question(
                    quiz,
                    "Why are data structures used?",
                    "To organize and store data efficiently",
                    "To replace the CPU",
                    "To remove algorithms",
                    "To shut down applications",
                    "A"));

            // Topic 2: Arrays and Linked Lists
            repository.save(new Question(
                    quiz,
                    "Which data structure generally provides fast indexed access?",
                    "Linked List",
                    "Array",
                    "Stack",
                    "Queue",
                    "B"));

            // Topic 3: Stacks and Queues
            repository.save(new Question(
                    quiz,
                    "Which principle does a stack follow?",
                    "FIFO",
                    "LIFO",
                    "Random order",
                    "Priority only",
                    "B"));

            // Topic 4: Trees and BST
            repository.save(new Question(
                    quiz,
                    "In a Binary Search Tree, smaller values are generally placed in the:",
                    "Right subtree",
                    "Root only",
                    "Left subtree",
                    "Queue",
                    "C"));

            // Topic 5: Graphs and Searching
            repository.save(new Question(
                    quiz,
                    "Which data structure is commonly used by BFS?",
                    "Stack",
                    "Queue",
                    "Array only",
                    "Heap only",
                    "B"));
        }

        // =========================================================
        // ARTIFICIAL INTELLIGENCE
        // =========================================================

        else if (courseTitle.equalsIgnoreCase("Artificial Intelligence")) {

            // Topic 1: Introduction to AI
            repository.save(new Question(
                    quiz,
                    "What is the main goal of Artificial Intelligence?",
                    "Creating systems capable of intelligent behavior",
                    "Only storing files",
                    "Only increasing computer memory",
                    "Replacing all programming languages",
                    "A"));

            // Topic 2: Intelligent Agents
            repository.save(new Question(
                    quiz,
                    "What does an intelligent agent use to perceive its environment?",
                    "Sensors",
                    "Compilers",
                    "Databases",
                    "Printers",
                    "A"));

            // Topic 3: Problem Solving and Search
            repository.save(new Question(
                    quiz,
                    "Which is an informed search algorithm?",
                    "A* Search",
                    "Simple sequential access",
                    "Random Search only",
                    "File Search",
                    "A"));

            // Topic 4: Knowledge Representation
            repository.save(new Question(
                    quiz,
                    "What is the purpose of knowledge representation?",
                    "To represent information so an AI system can reason about it",
                    "To delete knowledge",
                    "To increase screen resolution",
                    "To replace sensors",
                    "A"));

            // Topic 5: Responsible AI
            repository.save(new Question(
                    quiz,
                    "Which is an important consideration in Responsible AI?",
                    "Fairness and privacy",
                    "Ignoring security",
                    "Removing transparency",
                    "Avoiding accountability",
                    "A"));
        }
    }
}