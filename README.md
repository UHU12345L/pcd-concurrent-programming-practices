# Concurrent and Distributed Programming - Practices

**Course:** Concurrent and Distributed Programming (Programación Concurrente y Distribuida)\
**University:** Universidad de Huelva\
**Year:** 2026-2027

## 📄 Description

Practices of the PCD course, written in Java. Each practice builds on the previous one:
a circular queue implemented in Practice 1 is progressively extended until it becomes a
shared object used by concurrent threads.

## 📁 Repository Structure

| Folder | Practice | Topic |
|--------|----------|-------|
| `Practica1_PCD` | Practice 1 | Circular queue with array |
| `Practica2_PCD` | Practice 2 | Thread creation: producer and consumers |
| `Practica3_PCD` | Practice 3 | Synchronized queue with graphical view (Frame and Canvas) |
| `Practica4_PCD` | Practice 4 | Synchronization conditions with wait and notifyAll |

Each folder contains the NetBeans project and the assignment statement (PDF).

---

## 🔁 Practice 1 - Circular Queue with Array

### Description

First contact with Java and NetBeans. Implementation of a **circular queue** backed by a
fixed-size array: when the tail reaches the end of the array, new elements are stored again
at the beginning, reusing the slots freed by previously removed elements.

### 🎯 Learning Objectives

- Getting started with Java and the NetBeans environment
- Defining a contract with an **interface** and implementing it in a class
- Implementing a circular buffer with modular arithmetic
- Handling error cases (full / empty queue) with exceptions

### 🧩 Classes Implemented

- **ICola**: Interface with the queue operations: `acola`, `desacola`, `primero`, `getNum`
- **Cola**: Circular queue. `head` points to the first element and `tail` to the next free slot; both advance with `(pos + 1) % capacidad`. An element counter distinguishes the full and empty states (in both cases `head == tail`)
- **UsaCola**: Test program: a queue of capacity 4 and a 10-iteration loop that randomly inserts the iteration number or removes the first element

---

## 🧵 Practice 2 - Thread Creation: Producer and Consumers

### Description

Introduction to the two ways of creating threads in Java, using the queue from Practice 1
as a shared object. The queue becomes **ColaLenta** (slow queue): `acola` and `desacola`
sleep 100 ms between every instruction, which widens the window in which threads can
interfere with each other.

A **producer** thread inserts 10 random floats and, once it finishes, **two consumer**
threads extract numbers from the same queue at the same time. Since there is no mutual
exclusion, the consumers produce inconsistent results.

### 🎯 Learning Objectives

- Creating threads by **extending `Thread`** (Productor)
- Creating threads by **implementing `Runnable`** and wrapping them in a `Thread` (Consumidor)
- Starting threads with `start()` and waiting for them with `join()`
- Sharing one object between several threads
- Observing **race conditions** caused by the lack of mutual exclusion

### 🧩 Classes Implemented

- **ColaLenta**: Circular queue from Practice 1 with a `Thread.sleep(100)` between every instruction of `acola` and `desacola`
- **Productor**: Extends `Thread`. Inserts 10 random floats (0-100) and prints the thread id and each number
- **Consumidor**: Implements `Runnable`. Extracts 10 numbers and prints the thread id and each value
- **UsaCola**: Tests ColaLenta with a single thread, then runs one producer, waits for it, and runs two consumers at the same time

### 📊 Results

| Test | Threads | Result |
|------|---------|--------|
| ColaLenta alone | main only | ✅ Correct FIFO order |
| Producer | 1 producer | ✅ 10 numbers inserted correctly |
| Consumers | 2 consumers at once | ❌ Inconsistent: duplicated and lost values |

Example run with the two consumers (thread ids 37 and 38):

```
Identificador del hilo: 38 número extraido: 37.933395
Identificador del hilo: 37 número extraido: 37.933395
Identificador del hilo: 37 número extraido: 50.855762
Identificador del hilo: 38 número extraido: 50.855762
...
```

- Both consumers read the same `head` before either of them advances it → **the same number is extracted twice**
- Both then advance `head` → **the next number is skipped and lost**
- `numelementos` is updated by both at once, so the counter no longer matches the real content: in this run, 12 extractions were made from a queue holding 10 numbers, and 4 of the inserted numbers were never extracted

Results change on every run, depending on how the threads are scheduled.

---

## 🖼️ Practice 3 - Synchronized Queue with Graphical View

### Description

The race conditions observed in Practice 2 are fixed by making `acola` and `desacola`
**synchronized**, so only one thread at a time can modify the queue. A graphical window
(AWT `Frame` + `Canvas`) shows the queue in real time while a producer and a consumer work on it.

### 🎯 Learning Objectives

- Using `synchronized` methods to guarantee **mutual exclusion**
- Creating a window with `Frame` and drawing on it with `Canvas`
- Overriding `update` and `paint`, and using **double buffering** to avoid flickering

### 🧩 Classes Implemented

- **ColaLenta**: `acola` and `desacola` are now `synchronized`. Keeps a reference to the canvas and calls `representa` after every change and `avisa` when the queue is full or empty
- **CanvasCola**: Extends `Canvas`. Draws one box per queue slot, the `HEAD` and `TAIL` positions and the full/empty warnings. `update` only calls `paint`, which draws on an off-screen image first and then copies it to the screen
- **ColaFrame**: Extends `Frame`. Its `main` creates the window, canvas, queue and threads, waits for them with `join()`, waits 3 seconds and exits
- **Productor / Consumidor**: Both extend `Thread`

---

## ⏳ Practice 4 - Synchronization Conditions

### Description

Threads no longer fail immediately when the queue is full or empty: they **wait** for the
condition to change using `wait()`, and are woken up with `notifyAll()` every time an element
is inserted or removed.

- A producer that finds the queue full waits for a free slot. Every time a slot is freed, all
  waiting producers try again; the ones that don't get it wait again. After **3 attempts**,
  the producer gives up, throws the "queue full" exception and finishes.
- `desacola` behaves the same way when the queue is empty.
- When the consumer finishes, it calls `finalizar()`, which sets a shared flag and wakes up all
  waiting producers so they give up and the program can end.

Test: **4 producers** (15 elements each) and **1 consumer** (15 elements), all working at
random intervals between 1 and 3 seconds.

### 🎯 Learning Objectives

- Waiting for a condition with `wait()` inside a `while` loop
- Waking up waiting threads with `notifyAll()`
- Limiting the number of attempts and making threads give up
- Stopping waiting threads cleanly when no more changes will happen

### 🧩 Changes

- **ColaLenta**: `acola` and `desacola` wait with `while (condition && intentos < 3) wait();` and call `notifyAll()` after every change. New `fin` flag and `finalizar()` method
- **Productor**: Tries to insert 15 random floats, one every 1-3 s. If `acola` throws, the thread ends
- **Consumidor**: Extracts 15 elements, one every 1-3 s, and calls `finalizar()` when it finishes
- **ColaFrame**: Creates an array of 5 threads (4 producers and 1 consumer), starts them all and waits for all of them

### 📊 Results

| Check | Result |
|-------|--------|
| No duplicated values | ✅ Each number is inserted and extracted once |
| FIFO order | ✅ Numbers are extracted in the same order they were inserted |
| Queue size | ✅ Always between 0 and 5 |
| 3 attempts | ✅ Producers give up after their third failed attempt |
| End of consumer | ✅ Waiting producers give up immediately and the program ends |

Example: two producers on their third attempt compete for the same free slot. One gets it,
the other gives up:

```
Hilo: 46: desacola 50.12721 total: 4
Hilo: 44: acola 24.66089 total: 5
Hilo: 43: desisto
Productor: 43 termina Cola llena
```

The consumer extracts its 15 elements, while the producers can't insert all of theirs
(60 elements for a single consumer), so most of them end up giving up.

---

## 💻 Technologies Used

- **Language:** Java
- **IDE:** NetBeans

## 🚀 How to Run

1. Clone the repository
2. In NetBeans, open the project of the practice you want (*File → Open Project*):
   - Practice 1: `Practica1_PCD/P1/Practica1`
   - Practice 2: `Practica2_PCD/P2_PCD`
   - Practice 3: `Practica3_PCD/P3_PCD/Practica3`
   - Practice 4: `Practica4_PCD/P4_PCD/Practica4`
3. Run `UsaCola.java` (Practices 1-2) or `ColaFrame.java` (Practices 3-4)
