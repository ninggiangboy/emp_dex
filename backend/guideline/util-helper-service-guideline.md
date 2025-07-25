# Coding Rule: Distinguishing Between Util, Helper, and Service

## 📌 Overview

In a well-structured software project, especially following Domain-Driven Design (DDD) or Clean Architecture, it's
important to clearly separate concerns between utility classes, helpers, and services. This rule defines how and when to
use each.

---

## 📘 1. Util (Utility Classes)

### ✅ Purpose:

- Provide **generic, reusable, stateless functions**.
- Contain only `static` methods.
- No dependency injection.

### 🧱 Structure:

```java
public class StringUtils {
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
````

### 📎 Use Cases:

* String manipulation
* Date formatting
* Hashing
* File operations

---

## 📗 2. Helper Classes

### ✅ Purpose:

* Support functionality related to a specific domain or feature.
* Can be injected and depend on other beans.
* May contain state (usually stateless though).
* Acts like a "sub-service" but not core business logic.

### 🧱 Structure:

```java
@Component
public class JwtTokenHelper {
    public String generateToken(String userId) {
        // logic
    }

    public boolean validateToken(String token) {
        // logic
    }
}
```

### 📎 Use Cases:

* Token generation/validation
* Email formatting
* External API utilities (e.g., sending SMS)

---

## 📙 3. Service Classes

### ✅ Purpose:

* Contain **core business logic** (use cases).
* Inject dependencies (repositories, helpers, etc.).
* Marked with `@Service`.

### 🧱 Structure:

```java
@Service
public class UserService {
    public String login(String username, String password) {
        // business logic
    }
}
```

### 📎 Use Cases:

* User registration
* Order processing
* Payment handling

---

## ⚖️ Summary Comparison

| Category | Stateless | Static | Can Inject Beans | Contains Business Logic | Annotation   |
|----------|-----------|--------|------------------|-------------------------|--------------|
| Util     | ✅         | ✅      | ❌                | ❌                       | None         |
| Helper   | ✅/❌       | ❌/✅    | ✅ (if component) | ❌                       | `@Component` |
| Service  | ✅/❌       | ❌      | ✅                | ✅                       | `@Service`   |

---

## ✅ Rule of Thumb

* **Use `Util`** for generic functions with no dependencies.
* **Use `Helper`** for domain-specific utilities that assist services.
* **Use `Service`** to orchestrate and implement actual business logic.
