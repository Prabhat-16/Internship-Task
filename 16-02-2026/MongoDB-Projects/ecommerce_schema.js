// ============================================
// E-commerce Database - Schema & Sample Data
// ============================================

db = db.getSiblingDB("ecommerceDB");


db.Products.drop()
db.Customers.drop()
db.Orders.drop()

// ----------------------------
// Products
// ----------------------------
db.Products.insertMany([
  {
    name: "Laptop",
    category: "Electronics",
    price: 60000,
    stock: 20,
    reviews: []
  },
  {
    name: "Phone",
    category: "Electronics",
    price: 30000,
    stock: 30,
    reviews: []
  },
  {
    name: "Shoes",
    category: "Fashion",
    price: 2000,
    stock: 50,
    reviews: []
  }
])

// ----------------------------
// Customers
// ----------------------------
db.Customers.insertMany([
  { name: "Rahul", email: "rahul@gmail.com", city: "Mumbai" },
  { name: "Amit", email: "amit@gmail.com", city: "Delhi" }
])

let customer1 = db.Customers.findOne({ name: "Rahul" })
let product1 = db.Products.findOne({ name: "Laptop" })
let product2 = db.Products.findOne({ name: "Shoes" })

// ----------------------------
// Orders (Embedded Items)
// ----------------------------
db.Orders.insertOne({
  customerId: customer1._id,
  orderDate: new Date(),
  totalAmount: 62000,
  items: [
    {
      productId: product1._id,
      name: "Laptop",
      quantity: 1,
      price: 60000
    },
    {
      productId: product2._id,
      name: "Shoes",
      quantity: 1,
      price: 2000
    }
  ]
})

// Add Reviews (Embedded inside Products)
db.Products.updateOne(
  { _id: product1._id },
  {
    $push: {
      reviews: {
        customerId: customer1._id,
        rating: 5,
        comment: "Excellent Laptop!",
        reviewDate: new Date()
      }
    }
  }
)
