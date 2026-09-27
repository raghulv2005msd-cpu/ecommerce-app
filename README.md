# ShopSphere — Full Stack E-Commerce (Angular + Spring Boot)

A complete e-commerce starter with a Spring Boot REST API and an Angular 18
(standalone components) storefront: product catalog, JWT auth, cart, checkout,
order history, and an admin product manager.

## Project structure

```
ecommerce-app/
├── backend/     Spring Boot 3 (Java 17) REST API
└── frontend/    Angular 18 (standalone components) SPA
```

## Backend — run it

Requirements: Java 17+, Maven (or use the included wrapper if you add one).

```bash
cd backend
mvn spring-boot:run
```

The API starts on **http://localhost:8080**. It uses an in-memory H2 database
by default (data resets on every restart) — perfect for development. H2's web
console is available at `http://localhost:8080/h2-console`
(JDBC URL: `jdbc:h2:mem:ecommerce`, user `sa`, no password).

A default admin account is seeded automatically:
- **Email:** `admin@ecommerce.com`
- **Password:** `Admin123!`

### Switching to MySQL for production

Uncomment the MySQL block in `backend/src/main/resources/application.properties`
and comment out the H2 lines, then set `DB_USER` / `DB_PASSWORD` env vars (or
edit the file directly). Create the database first: `CREATE DATABASE ecommerce_db;`

### Key REST endpoints

| Method | Endpoint                  | Auth        | Purpose                     |
|--------|---------------------------|-------------|------------------------------|
| POST   | `/api/auth/register`      | Public      | Create an account            |
| POST   | `/api/auth/login`         | Public      | Get a JWT                    |
| GET    | `/api/products`           | Public      | List/search products         |
| GET    | `/api/products/{id}`      | Public      | Product details              |
| POST   | `/api/products`           | Admin       | Create product               |
| PUT    | `/api/products/{id}`      | Admin       | Update product                |
| DELETE | `/api/products/{id}`      | Admin       | Soft-delete product           |
| GET    | `/api/cart`                | User        | View cart                     |
| POST   | `/api/cart/items`          | User        | Add/update a cart line        |
| DELETE | `/api/cart/items/{id}`     | User        | Remove a cart line            |
| POST   | `/api/orders/checkout`     | User        | Place an order from the cart  |
| GET    | `/api/orders`              | User        | Order history                 |

All authenticated requests need `Authorization: Bearer <token>`.

## Frontend — run it

Requirements: Node.js 18+, npm.

```bash
cd frontend
npm install
npm start
```

Opens on **http://localhost:4200** and talks to the backend at
`http://localhost:8080/api` (configured in `src/environments/environment.ts`).

### What's included

- **Product catalog** with search, and a detail page
- **JWT auth** (register/login), token stored client-side and attached via an
  HTTP interceptor
- **Cart** and **checkout** flow that calls the real backend, decrements stock,
  and creates an order
- **Order history** page
- **Admin product manager** (`/admin/products`, visible only to `ROLE_ADMIN`)
  for creating, editing, and removing products
- Route guards (`authGuard`, `adminGuard`) protecting private/admin routes

### Building for production

```bash
npm run build
```

Outputs static files to `dist/ecommerce-frontend`. Update
`src/environments/environment.prod.ts` with your deployed API URL first.

## Opening in VS Code

Open `ecommerce-app.code-workspace` (File → Open Workspace from File) rather
than the plain folder — it sets up a multi-root workspace with `backend` and
`frontend` as separate roots, and recommends the extensions below.

**Recommended extensions** (VS Code will prompt you to install these):
- Extension Pack for Java + Spring Boot Extension Pack (backend debugging/run)
- Angular Language Service (template intellisense)
- ESLint, Prettier, EditorConfig

**Run/debug from the editor** (`Run and Debug` panel, or `Cmd/Ctrl+Shift+P` →
`Tasks: Run Task`):
- **Debug Spring Boot Backend** — starts the API with breakpoints enabled
- **Launch Angular in Chrome** — serves the frontend and opens it in a
  debuggable Chrome instance, with TypeScript breakpoints working directly
  in `.ts` files
- **Debug Full Stack (Backend + Frontend)** — compound config that starts both
- Tasks: **Run Spring Boot Backend**, **Serve Angular Frontend**, **Install
  Frontend Dependencies**, **Build Spring Boot Backend**, **Build Angular
  Frontend (production)**, and **Run Full Stack** (starts both without
  debugging)

## Notes & next steps

This is a solid, working foundation — not a "batteries included" enterprise
system. A few things worth adding before going to production:
- Real payment integration (Stripe/PayPal) — checkout currently marks orders
  `PAID` immediately for simplicity
- Refresh tokens / token expiry handling on the frontend
- Pagination on the product list
- Image uploads instead of raw image URLs
- Unit/integration tests
