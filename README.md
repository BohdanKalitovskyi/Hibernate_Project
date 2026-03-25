# Hibernate Project

A console-based Java application demonstrating **Hibernate ORM** with **Spring DI** and **PostgreSQL**, built as a client order management system.

## Tech Stack

- Java 17
- Hibernate 6
- Spring Context (DI only, no Spring Boot)
- PostgreSQL
- Maven

## Entity Model

- **Client 1:1 Profile**: Each client has one unique personal profile.
- **Client 1:N Order**: A client can place multiple orders; each order belongs to one client.
- **Client N:M Coupon**: Users and Coupons are linked via a `client_coupons` join table (many-to-many).

## Hibernate Concepts Demonstrated

| Concept | Where |
|---|---|
| All 4 JPA relationship types | `Client`, `Order`, `Profile`, `Coupon` |
| `CascadeType.REMOVE` | Client → Profile, Client → Orders |
| `FetchType.EAGER` | Orders and Coupons on Client |
| Parameterized HQL query | `ClientService.findByRegistrationYear()` |
| Native SQL query | `CouponService.addCouponToUser()` |
| Transaction management | `TransactionHelper` (commit/rollback) |
| Schema generation | `hbm2ddl.auto = create-drop` |
| `nullable`, `unique` constraints | All `@Column` annotations |

## Setup

1. Create a PostgreSQL database (default: `postgres` on port `5432`)
2. Update credentials in `HibernateConfiguration.java` if needed
3. Run `Main.java` — schema is created automatically on startup

## Features

- Add / update / delete clients
- Add / update client profile
- Create orders and cancel them
- Create coupons and assign to clients
- Search clients by registration year (HQL)
- View client orders, coupons, and profile
