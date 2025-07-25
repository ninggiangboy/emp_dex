# 📘 Class Naming Conventions in Domain-Driven Design (DDD)

> Use the Ubiquitous Language to guide naming. Names must reflect the domain's meaning and intention.

---

## 🎯 Goals

- Make class names reflect **business concepts**.
- Promote **readability**, **consistency**, and **collaboration** between devs and domain experts.
- Help enforce the **layered structure** of DDD: Application, Domain, Infrastructure.

---

## 🧱 General Naming Guidelines

| Layer                       | Naming Convention                            | Examples                                         |
|-----------------------------|----------------------------------------------|--------------------------------------------------|
| **Entity / Aggregate Root** | Noun (domain concept)                        | `Order`, `Customer`, `Invoice`                   |
| **Value Object**            | Noun or Descriptor                           | `Money`, `EmailAddress`, `Coordinates`           |
| **Domain Service**          | `*Service` (with meaningful verb or concept) | `PricingService`, `FraudDetectionService`        |
| **Application Service**     | `*ApplicationService`, `*UseCase`            | `PlaceOrderService`, `RegisterUserUseCase`       |
| **Repository Interface**    | `*Repository`                                | `OrderRepository`, `CustomerRepository`          |
| **Factory**                 | `*Factory`                                   | `OrderFactory`, `UserFactory`                    |
| **Event**                   | Past tense or `*Event`                       | `OrderPlacedEvent`, `UserRegistered`             |
| **Command**                 | Action phrase                                | `PlaceOrderCommand`, `UpdateCustomerCommand`     |
| **DTO / Response**          | Ends with `Dto`, `Response`, or `ViewModel`  | `OrderDto`, `CustomerResponse`, `OrderViewModel` |
| **Specification**           | `*Specification`                             | `EligibleForPromotionSpecification`              |

---

## 🔍 Entity Naming

- Reflect **real-world nouns**.
- Example: `Order`, `BankAccount`, `ProductCatalog`
- Avoid vague names like `Data`, `Info`, `Handler`

```java
public class Order {
    private List<Item> items;
    public void confirm() { ... }
}
````

---

## 🧩 Value Object Naming

* Represent **a concept, not identity**
* Should **sound like a unit of meaning**

```java
public class Money {
    private BigDecimal amount;
    private String currency;
}
```

---

## ⚙️ Domain Service Naming

* Use a **meaningful domain term** + `Service`
* Avoid techy or generic names like `Processor`, `Manager`, `Handler`

```java
public class DiscountPolicyService {
    public Money calculateDiscount(Order order) { ... }
}
```

---

## 🎯 Application Service Naming

* Reflect **use-case**: `PlaceOrderService`, `TransferMoneyUseCase`
* Can end with `UseCase` or `ApplicationService` for clarity

```java
public class RegisterCustomerUseCase {
    public void execute(RegisterCustomerCommand command) { ... }
}
```

---

## 🏗️ Repository Naming

* Interface: `*Repository`
* Implementation: `Jpa*Repository`, `Mongo*Repository`, or suffix with `Impl`

```java
public interface OrderRepository {
    Order findById(OrderId id);
}
```

---

## 📢 Event Naming

* Past tense, something that **happened**

```java
public class OrderShippedEvent {
    private OrderId orderId;
}
```

---

## 📜 Command / Query Naming

| Type    | Convention              | Example                                     |
|---------|-------------------------|---------------------------------------------|
| Command | `Verb + Noun + Command` | `PlaceOrderCommand`                         |
| Query   | `Get/Find + Entity`     | `FindOrderQuery`, `GetCustomerByEmailQuery` |

---

## ✅ Do vs ❌ Don't

| Do                                         | Don't                                      |
|--------------------------------------------|--------------------------------------------|
| `Order`, `Money`, `DiscountPolicyService`  | `OrderData`, `Utils`, `Handler`            |
| `RegisterUserUseCase`, `PlaceOrderCommand` | `DoThings`, `ProcessRequest`, `Service123` |
| `CustomerRepository`                       | `CustomerDAO` (DAO = persistence concern)  |

---

## 🧭 Suggested Package Structure

```
src/
├── application/
│   └── PlaceOrderUseCase.java
├── domain/
│   ├── model/
│   │   └── Order.java
│   ├── service/
│   │   └── DiscountPolicyService.java
│   ├── event/
│   │   └── OrderPlacedEvent.java
│   └── valueobject/
│       └── Money.java
├── infrastructure/
│   └── repository/
│       └── JpaOrderRepository.java
```

---

## 🏁 Summary

| Layer          | Naming Style                 | Purpose                  |
|----------------|------------------------------|--------------------------|
| Domain         | Reflect real domain language | Core business logic      |
| Application    | Action-oriented              | Use case orchestration   |
| Infrastructure | Technical concern            | Persistence, integration |

Stick to your **Ubiquitous Language** and let the code read like a **conversation with the business**.