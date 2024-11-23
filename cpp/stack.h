#include <stdexcept>
#include <string>
#include <memory>
using namespace std;


#define MAX_CAPACITY 32768
#define INITIAL_CAPACITY 16

template <typename T>
class Stack {
  unique_ptr<T[]> elements;
  int capacity;
  int top;
  Stack(const Stack<T>&) = delete;
  Stack<T>& operator=(const Stack<T>&) = delete;

public:
  Stack():
    top(0),
    capacity(INITIAL_CAPACITY),
    elements(make_unique<T[]>(INITIAL_CAPACITY)) {
  }

  int size() const {
    return top;
  }

  bool is_empty() const {
    return top == 0;
  }
  
  bool is_full() const {
    return top == MAX_CAPACITY;
  }

  void push(T element) {
    if (top == MAX_CAPACITY) {
      throw overflow_error("Stack has reached maximum capacity");
    }
    if (top == capacity){
      reallocate(capacity * 2);
    }
    elements[top++] = element;
  }

  T pop() {
    if (is_empty()) {
      throw underflow_error("cannot pop from empty stack");
    }
    if (top < capacity / 4) {
      reallocate(capacity / 2);
    }
    T popped_item = elements[top - 1];
    top = top - 1;
    return popped_item;
  }

private:
  void reallocate(int new_capacity) {
    if (new_capacity >= MAX_CAPACITY) {
      new_capacity = MAX_CAPACITY;
    } else if (new_capacity < INITIAL_CAPACITY) {
      new_capacity = INITIAL_CAPACITY;
    }

    unique_ptr<T[]> new_elements = make_unique<T[]>(new_capacity);
    copy(elements.get(), elements.get() + top, new_elements.get());
    elements = move(new_elements);
    capacity = new_capacity;
  }

};
