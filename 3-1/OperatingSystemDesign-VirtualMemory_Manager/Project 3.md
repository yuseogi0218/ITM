## [code-FIFO](https://github.com/yuseogi0218/ITM/tree/main/3-1/OperatingSystemDesign-VirtualMemory_Manager/FIFO)
## [code-LRU](https://github.com/yuseogi0218/ITM/tree/main/3-1/OperatingSystemDesign-VirtualMemory_Manager/LRU)
## [report](https://github.com/yuseogi0218/ITM/blob/main/3-1/OperatingSystemDesign-VirtualMemory_Manager/report/report.md)

 
**Project 3: Designing a Virtual Memory Manager** 

- Objectives
  - To be sure of understanding the concept of virtual memory
  - To understand how the memory management system can be implemented in practice
  - To be able to add a new feature from existing memory management functions
- Procedure
  - Read the description in Programming Project supported as image files (pp.452~456)
  - Run the project that has been given through eclass website
  - The project has a completed version of the basic implementation.
  - Run the project and see what happens
  - Analyze the source codes in the project to understand how the required functions are implemented
  - Give **three-page report** at the end of this lab to show me the degree of your understanding
  - Make your report be precise and easily understandable so that I can implement the project only with your report
  - Get 100 pts if you complete step 1~8.
  - Implement ‘Modifications’ part in page 456.
    - Change the physical memory accommodates only **128** frames
    - Need **page replacement** to handle all memory references
      - Implement **two page replacement algorithms** (two among LRU, LRU approximation, and FIFO)
      - Complete the page replacement part with the above algorithms
      - Plug the page replacement part on the initial implementation given to you
        - The statistics add the number of page replacements 
    - Analyze the result with the original implementation given to you
    - Also describe the difference of the results with two replacement algorithms are applied each.
    - The report will focus on the difference between the original and your modification
    - Get 300 points after completing step 9.


