# 📘 Ubiquitous Language Guide

> A consistent, shared language between developers and domain experts.

---

## 🧠 What is Ubiquitous Language?

**Ubiquitous Language** is a central concept in Domain-Driven Design (DDD), referring to a **shared, consistent
vocabulary** used by both technical and non-technical stakeholders to describe the domain and its operations.

> 🗣️ It bridges the gap between **business experts** and **software developers**.

---

## 🎯 Goals of Ubiquitous Language

- Ensure **clarity** across code, documents, discussions.
- Avoid miscommunication or misunderstanding in domain rules.
- Make **code readable** by domain experts.
- Drive the **design of the model** and the software itself.

---

## 🏗️ Where Is It Used?

Ubiquitous Language is used in **every layer of the software**:

| Area                  | Example                                               |
|-----------------------|-------------------------------------------------------|
| Class Names           | `Invoice`, `Customer`, `ShipmentPolicy`               |
| Method Names          | `completeOrder()`, `cancelReservation()`              |
| UI Labels             | “Customer Segment”, “Shipping Region”                 |
| API Contracts         | `/orders/{id}/cancel`, `GET /shipments`               |
| Documentation & Specs | “A customer can cancel an order if it is not shipped” |

---

## ✅ Characteristics

| Characteristic | Description                                                         |
|----------------|---------------------------------------------------------------------|
| **Shared**     | Used by developers, product owners, domain experts                  |
| **Consistent** | Same term = same meaning across system                              |
| **Refined**    | Evolves as team learns more about the domain                        |
| **Precise**    | Terms should avoid ambiguity (e.g., "confirm", "submit", "process") |

---

## 🧭 Examples

### ❌ Bad

- Class name: `UserProcessor`
- Method: `doStuff()`
- API: `POST /execute`

### ✅ Good

- Class name: `OrderConfirmationService`
- Method: `confirmOrder()`
- API: `POST /orders/{id}/confirm`

---

## 💡 How to Develop a Ubiquitous Language

1. **Talk with domain experts** frequently.
2. **Identify terms** they use and the meaning behind them.
3. **Reflect those terms** in code, UI, and docs.
4. **Review and evolve** language when the domain changes.
5. Use tools like **Glossary documents** or **Living Documentation** to track terms.

---

## 🧩 Real-world Example

### Domain: Food Delivery

| Term (Ubiquitous) | Description                               |
|-------------------|-------------------------------------------|
| `Courier`         | Person delivering the food                |
| `DeliveryWindow`  | Time period in which delivery is expected |
| `DispatchOrder()` | Assign a courier to an order              |
| `Customer`        | End-user placing an order                 |

These terms should appear consistently in:

- Codebase (`Courier.java`, `Order.dispatch()`)
- API contracts (`POST /orders/{id}/dispatch`)
- Business rules: “A courier cannot be assigned to more than 5 orders concurrently”

---

## 🔁 Ubiquitous Language vs. Terminology Drift

> ❌ Drift occurs when multiple terms are used inconsistently to describe the same concept.

| Example of Drift             | Resolution                                |
|------------------------------|-------------------------------------------|
| `Client`, `User`, `Customer` | Use only **`Customer`** across all layers |
| `Checkout`, `Place Order`    | Choose one and standardize                |

---

## 🧰 Tools to Support Ubiquitous Language

- Domain glossary (Notion, Confluence, etc.)
- Code review checklist: "Is this naming aligned with domain language?"
- Collaborative modeling workshops (e.g., Event Storming, CRC cards)
- Living documentation / diagrams with consistent terms

---

## 🏁 Summary

| Do                       | Don’t                               |
|--------------------------|-------------------------------------|
| Use domain terms in code | Invent generic or ambiguous names   |
| Talk with domain experts | Assume your mental model is correct |
| Be consistent everywhere | Allow terminology to drift          |
| Evolve the language      | Let it become outdated              |