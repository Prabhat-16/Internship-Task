import streamlit as st
import pandas as pd
import plotly.express as px
import plotly.graph_objects as go
from datetime import datetime

# --- Page Config ---
st.set_page_config(
    page_title="E-Commerce Pro Dashboard",
    page_icon="📊",
    layout="wide",
    initial_sidebar_state="expanded",
)

# --- Custom CSS for Styling ---
st.markdown("""
<style>
    /* Main background */
    .main {
        background-color: #f8f9fa;
    }
    
    /* Custom Card Styling */
    .metric-card {
        background-color: white;
        padding: 20px;
        border-radius: 12px;
        box-shadow: 0 4px 6px rgba(0,0,0,0.05);
        border: 1px solid #eef0f2;
        text-align: center;
    }
    
    .metric-label {
        font-size: 14px;
        color: #6c757d;
        font-weight: 600;
        text-transform: uppercase;
        margin-bottom: 8px;
    }
    
    .metric-value {
        font-size: 28px;
        color: #2b3a4c;
        font-weight: 700;
    }
    
    /* Sidebar styling */
    .sidebar .sidebar-content {
        background-color: #2b3a4c;
    }
    
    /* Title styling */
    .title-container {
        display: flex;
        align-items: center;
        gap: 15px;
        margin-bottom: 25px;
    }
    
    .stPlotlyChart {
        border-radius: 12px;
        overflow: hidden;
        box-shadow: 0 4px 10px rgba(0,0,0,0.03);
    }
</style>
""", unsafe_allow_html=True)

# --- Load Data ---
@st.cache_data
def load_data():
    df = pd.read_csv("orders.csv")
    df['order_date'] = pd.to_datetime(df['order_date'])
    return df

df = load_data()

# --- Sidebar Filters ---
with st.sidebar:
    st.image("https://cdn-icons-png.flaticon.com/512/3081/3081559.png", width=80)
    st.title("Filters")
    
    # Date Filter
    st.subheader("📅 Date Range")
    min_date = df['order_date'].min()
    max_date = df['order_date'].max()
    
    start_date = st.date_input("Start Date", min_date, min_value=min_date, max_value=max_date)
    end_date = st.date_input("End Date", max_date, min_value=min_date, max_value=max_date)
    
    # Category Filter
    st.subheader("📁 Categories")
    all_categories = ["All"] + list(df['category'].unique())
    selected_category = st.selectbox("Select Category", all_categories)
    
    # Product Filter
    st.subheader("🛍️ Products")
    if selected_category == "All":
        product_list = ["All"] + list(df['product_name'].unique())
    else:
        product_list = ["All"] + list(df[df['category'] == selected_category]['product_name'].unique())
    selected_product = st.selectbox("Select Product", product_list)

    st.markdown("---")
    st.info("💡 Pro Tip: Use the date range to see seasonal trends.")

# --- Filter Logic ---
filtered_df = df[(df['order_date'] >= pd.to_datetime(start_date)) &
                 (df['order_date'] <= pd.to_datetime(end_date))]

if selected_category != "All":
    filtered_df = filtered_df[filtered_df['category'] == selected_category]

if selected_product != "All":
    filtered_df = filtered_df[filtered_df['product_name'] == selected_product]

# --- Header Section ---
st.markdown("""
    <div class="title-container">
        <h1 style='margin:0; color: #2b3a4c; font-size: 2.5rem;'>🚀 E-Commerce Analytics Pro</h1>
    </div>
""", unsafe_allow_html=True)

# --- KPI Section ---
total_revenue = filtered_df['total_amount'].sum()
total_orders = filtered_df.shape[0]
avg_order_value = total_revenue / total_orders if total_orders > 0 else 0
unique_customers = filtered_df['order_id'].nunique() # Assuming order_id is unique per transaction for now

c1, c2, c3, c4 = st.columns(4)

with c1:
    st.markdown(f"""
        <div class="metric-card">
            <div class="metric-label">💰 Total Revenue</div>
            <div class="metric-value">₹{total_revenue:,.0f}</div>
        </div>
    """, unsafe_allow_html=True)

with c2:
    st.markdown(f"""
        <div class="metric-card">
            <div class="metric-label">📦 Total Orders</div>
            <div class="metric-value">{total_orders}</div>
        </div>
    """, unsafe_allow_html=True)

with c3:
    st.markdown(f"""
        <div class="metric-card">
            <div class="metric-label">🧾 Avg Order Value</div>
            <div class="metric-value">₹{avg_order_value:,.0f}</div>
        </div>
    """, unsafe_allow_html=True)

with c4:
    st.markdown(f"""
        <div class="metric-card">
            <div class="metric-label">👥 Unique Customers</div>
            <div class="metric-value">{unique_customers}</div>
        </div>
    """, unsafe_allow_html=True)

st.write("") # Spacer

# --- Middle Section: Charts ---
row2_col1, row2_col2 = st.columns([2, 1])

with row2_col1:
    # Monthly Sales Trend
    monthly_sales = filtered_df.copy()
    monthly_sales['month_year'] = monthly_sales['order_date'].dt.strftime('%b %Y')
    sales_trend = monthly_sales.groupby('month_year')['total_amount'].sum().reset_index()
    
    # Sort by date properly
    sales_trend['sort_date'] = pd.to_datetime(sales_trend['month_year'])
    sales_trend = sales_trend.sort_values('sort_date')

    fig_sales = px.area(sales_trend, 
                        x='month_year', 
                        y='total_amount',
                        title="📈 Monthly Sales Trend",
                        labels={'total_amount': 'Revenue (₹)', 'month_year': 'Month'},
                        color_discrete_sequence=['#4e73df'])
    
    fig_sales.update_layout(
        plot_bgcolor='rgba(0,0,0,0)',
        paper_bgcolor='rgba(0,0,0,0)',
        xaxis=dict(showgrid=False),
        yaxis=dict(showgrid=True, gridcolor='#eef0f2'),
        margin=dict(l=20, r=20, t=50, b=20),
        font=dict(family="Inter, sans-serif", size=12, color="#2b3a4c")
    )
    st.plotly_chart(fig_sales, use_container_width=True)

with row2_col2:
    # Category Distribution
    category_sales = filtered_df.groupby('category')['total_amount'].sum().reset_index()
    
    fig_pie = px.pie(category_sales, 
                     values='total_amount', 
                     names='category', 
                     title="🍰 Sales by Category",
                     hole=0.6,
                     color_discrete_sequence=px.colors.qualitative.Pastel)
    
    fig_pie.update_layout(
        showlegend=True,
        legend=dict(orientation="h", yanchor="bottom", y=-0.2, xanchor="center", x=0.5),
        margin=dict(l=20, r=20, t=50, b=20)
    )
    st.plotly_chart(fig_pie, use_container_width=True)

# --- Lower Section ---
st.write("")
row3_col1, row3_col2 = st.columns([1, 1])

with row3_col1:
    # Top Products
    top_products = filtered_df.groupby('product_name')['total_amount'].sum().reset_index()
    top_products = top_products.sort_values(by='total_amount', ascending=True).tail(5)
    
    fig_products = px.bar(top_products,
                          x='total_amount',
                          y='product_name',
                          orientation='h',
                          title="🏆 Top 5 Performing Products",
                          labels={'total_amount': 'Total Sales (₹)', 'product_name': 'Product'},
                          color='total_amount',
                          color_continuous_scale='Viridis')
    
    fig_products.update_layout(
        plot_bgcolor='rgba(0,0,0,0)',
        paper_bgcolor='rgba(0,0,0,0)',
        xaxis=dict(showgrid=True, gridcolor='#eef0f2'),
        yaxis=dict(showgrid=False),
        margin=dict(l=20, r=20, t=50, b=20)
    )
    st.plotly_chart(fig_products, use_container_width=True)

with row3_col2:
    # Recent Transactions Table
    st.markdown("<h4 style='padding: 10px 0;'>📝 Recent Transactions</h4>", unsafe_allow_html=True)
    recent_transactions = filtered_df.sort_values(by='order_date', ascending=False).head(10)
    st.dataframe(recent_transactions[['order_date', 'product_name', 'category', 'total_amount']], 
                 use_container_width=True,
                 hide_index=True)

# --- Footer ---
st.markdown("---")
st.markdown("<p style='text-align: center; color: #6c757d;'>Data Last Updated: {}</p>".format(datetime.now().strftime("%Y-%m-%d %H:%M")), unsafe_allow_html=True)