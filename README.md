# CLC
### Simple Java Interpreter
A basic interpreter written in Java that can process mathematical expressions with numbers and variables.

### Features
- Lexical Analysis: Tokenizes input into numbers, identifiers, and operators

- Parsing: Understands basic arithmetic expressions

- Evaluation: Computes results of mathematical operations

- Supported Operations: Addition (+), Subtraction (-), Multiplication (*), Division (/)

- Variables: Supports identifier storage and retrieval

### Usage
- Compile the Java source files

- Run the interpreter

- Enter expressions when prompted

Example: 
- Numerical Operations
```bash
>>> 5
5

>>> 9+5
14

>>> 74*(41+36)
5698

```
- Print Statements (Currently only For Integers)
```
>>> write (8/7)
Result: 1

>>> write (4+7*(74-70)/(5-3))
Result: 18

```
- Variables and Identifiers
```
>>> assume x=86

>>> assume y=74

>>> x+y
160

>>> x-y
12

>>> write(x*y+5)
Result: 6369

>>> assume arnab=54

>>> arnab - 40

14
```

This is a learning project demonstrating fundamental interpreter concepts in Java.
