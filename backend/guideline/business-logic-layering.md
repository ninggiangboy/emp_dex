# 📘 Business Logic Layering Guide (DDD Style)

This document describes how to structure and separate business logic across different layers in a Domain-Driven Design (
DDD) system: `Application Service`, `Domain Service`, `Entity`, and `Value Object`.

---

## 🎯 Goal

To keep the codebase **modular**, **testable**, and **maintainable**, by putting business logic in the **right place**.

---

## 📚 Overview: Where Business Logic Should Go?

| Layer                    | Responsibility                           | Business Logic?      | Can Call                         |
|--------------------------|------------------------------------------|----------------------|----------------------------------|
| **Application Service**  | Coordinates use-case execution           | ❌ Orchestration only | Domain services, entities, repos |
| **Domain Service**       | Domain logic not belonging to one entity | ✅ Yes                | Entities, value objects          |
| **Entity / Aggregate**   | Business logic with state                | ✅ Yes                | Value objects, domain services   |
| **Value Object**         | Immutable, simple logic                  | ✅ (limited)          | Internal calculation only        |
| **Repository / Adapter** | Persistence or integration layer         | ❌ No                 | External infrastructure          |

---

## 📗 1. Application Service – "Orchestrator"

### ✅ Role

- Execute **use-cases**.
- Orchestrate calls between repositories, domain models, and external services.
- Do **not** contain business logic.

### 🧱 Example

```java
@Service
public class OrderApplicationService {
    public void placeOrder(CreateOrderRequest req) {
        Order order = orderFactory.create(req);
        order.validate();
        orderRepository.save(order);
        eventPublisher.publish(new OrderPlacedEvent(order.getId()));
    }
}
````

---

## 📘 2. Domain Service – "Cross-Entity Business Logic"

### ✅ Role

* Handle **complex business rules** not naturally belonging to one entity.
* Used when logic involves **multiple aggregates or external conditions**.

### 🧱 Example

```java
@Service
public class FriendshipPolicy {
    public boolean canBefriend(User user1, User user2) {
        return !user1.hasBlocked(user2) && !user2.hasBlocked(user1);
    }
}
```

---

## 📙 3. Entity / Aggregate – "The Core"

### ✅ Role

* Encapsulate **business rules tightly coupled with state**.
* Entities should enforce **invariants** and **validate operations**.

### 🧱 Example

```java
public class Order {
    private List<Item> items;
    private boolean confirmed;

    public void confirm() {
        if (items.isEmpty()) throw new BusinessException("Order must have items");
        this.confirmed = true;
    }
}
```

---

## 📒 4. Value Object – "Simple, Immutable Logic"

### ✅ Role

* Represent **immutable concepts** (like money, email).
* Contain simple self-contained logic.

### 🧱 Example

```java
public class Money {
    private final BigDecimal amount;
    private final String currency;

    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) throw new CurrencyMismatchException();
        return new Money(this.amount.add(other.amount), this.currency);
    }
}
```

---

## 🤔 Where Should Logic Go?

| Question                                               | Place                 |
|--------------------------------------------------------|-----------------------|
| Is it tied to one entity’s state?                      | ✅ Entity              |
| Is it logic that spans multiple entities?              | ✅ Domain Service      |
| Is it orchestration of flow or calling services/repos? | ✅ Application Service |
| Is it an immutable, reusable type with logic?          | ✅ Value Object        |

---

## ✅ Design Principles

* **"Fat domain, thin service"**: push business rules into domain models.
* Keep **application service** lightweight and focused on coordination.
* Ensure **domain layer is pure**, free of infrastructure concerns.
* Value Objects should be **immutable and self-validating**.

---

## 📁 Suggested Project Structure

```
src/
├── application/
│   └── OrderApplicationService.java
├── domain/
│   ├── model/
│   │   └── Order.java
│   ├── service/
│   │   └── DiscountPolicy.java
│   └── valueobject/
│       └── Money.java
├── infrastructure/
│   └── persistence/
│       └── OrderRepositoryImpl.java
```

---

## 📌 Summary

Organizing business logic properly ensures:

* Better **testability**
* Cleaner **domain models**
* Easier **refactoring**
* Stronger **separation of concerns**

This rule is a core part of building maintainable systems using **Domain-Driven Design (DDD)**.