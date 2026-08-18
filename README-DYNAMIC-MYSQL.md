# Smart Canteen - Dynamic MySQL Setup

## Architecture

React (`http://localhost:5173`) -> Spring Boot (`http://localhost:8080`) -> MySQL (`smart_canteen`)

The application now uses the database for:

- `categories`
- `foods`
- `users`
- `employees`
- `cart`
- `orders`
- `order_items`

The old `localStorage` menu/order stores are no longer used by the application pages.

## MySQL

Create the database once:

```sql
CREATE DATABASE IF NOT EXISTS smart_canteen;
```

The current `application.properties` expects:

```text
username=root
password=root
database=smart_canteen
port=3306
```

Change those values if your local MySQL password is different.

Hibernate uses `ddl-auto=update`, so the tables/columns are created or updated automatically.

## Backend

Open:

```text
smart-canteen-backend
```

Then:

```powershell
.\mvnw.cmd spring-boot:run
```

Backend runs on:

```text
http://localhost:8080
```

If PowerShell says `.\mvnw.cmd` is not recognized, make sure the terminal is inside the `smart-canteen-backend` folder.

## Frontend

Open:

```text
smart-canteen
```

Install dependencies:

```powershell
npm install
```

Run:

```powershell
npm run dev
```

Frontend runs on:

```text
http://localhost:5173
```

## Cart API

Add:

```http
POST /api/cart
Content-Type: application/json

{
  "userId": 1,
  "foodId": 5,
  "quantity": 1
}
```

If the food is already in the user's cart, the backend increases its quantity instead of creating a duplicate row.

Update:

```http
PUT /api/cart/{cartId}

{
  "quantity": 3
}
```

Delete:

```http
DELETE /api/cart/{cartId}
```

Clear a user's cart:

```http
DELETE /api/cart/user/{userId}
```

## Important

After replacing the old project with this version, refresh the browser and log in again. The JWT is stored in `smartCanteenToken`; menu/cart/order data comes from the backend database.
