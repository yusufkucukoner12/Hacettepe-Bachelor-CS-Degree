import os
import random

def create_input_files_csv(num_files, directory):
    """
    Create a specified number of input files with unique test data in CSV format for the assignment.

    Parameters:
    num_files (int): The number of files to create
    directory (str): The directory where the files will be saved
    """
    if not os.path.exists(directory):
        os.makedirs(directory)

    for file_num in range(1, num_files + 1):
        file_name = f"sectors_{file_num}.dat"
        file_path = os.path.join(directory, file_name)

        with open(file_path, 'w') as file:
            # Writing header
            file.write("X,Y,Z\n")
            file.write("0,0,0\n")

            # Generating random coordinates (X, Y, Z) for space sectors
            for _ in range(random.randint(5, 50)):  # Random number of lines per file
                x = random.randint(-100, 100)
                y = random.randint(-100, 100)
                z = random.randint(-100, 100)
                file.write(f"{x},{y},{z}\n")

# Directory to save the input files
directory_csv = "inputs"

# Creating 50 input files in CSV format
create_input_files_csv(50, directory_csv)

print(f"50 input files have been created in {directory_csv}")
