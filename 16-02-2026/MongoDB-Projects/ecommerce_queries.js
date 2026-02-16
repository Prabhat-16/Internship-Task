db = db.getSiblingDB("ecommerceDB");

// ============================================
// E-commerce - Advanced Queries
// ============================================

// 1️⃣ Top-Selling Products
db.Orders.aggregate([
  { $unwind: "$items" },
  {
    $group: {
      _id: "$items.name",
      totalSold: { $sum: "$items.quantity" }
    }
  },
  { $sort: { totalSold: -1 } }
])

// 2️⃣ Top-Selling Products Per Category
db.Orders.aggregate([
  { $unwind: "$items" },
  {
    $lookup: {
      from: "Products",
      localField: "items.productId",
      foreignField: "_id",
      as: "productDetails"
    }
  },
  { $unwind: "$productDetails" },
  {
    $group: {
      _id: {
        category: "$productDetails.category",
        product: "$items.name"
      },
      totalSold: { $sum: "$items.quantity" }
    }
  },
  { $sort: { totalSold: -1 } }
])

// 3️⃣ Customer Purchase Pattern
db.Orders.aggregate([
  {
    $group: {
      _id: "$customerId",
      totalSpent: { $sum: "$totalAmount" },
      totalOrders: { $sum: 1 }
    }
  },
  { $sort: { totalSpent: -1 } }
])

// 4️⃣ Monthly Revenue
db.Orders.aggregate([
  {
    $group: {
      _id: {
        year: { $year: "$orderDate" },
        month: { $month: "$orderDate" }
      },
      totalRevenue: { $sum: "$totalAmount" }
    }
  },
  { $sort: { "_id.year": 1, "_id.month": 1 } }
])

// 5️⃣ Yearly Revenue
db.Orders.aggregate([
  {
    $group: {
      _id: { year: { $year: "$orderDate" } },
      totalRevenue: { $sum: "$totalAmount" }
    }
  }
])
