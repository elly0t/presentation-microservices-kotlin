import React from "react";
import { BrowserRouter, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import ForgotPassword from "./screens/ForgotPassword";
import App from "./screens/Home";
import Login from "./screens/Login";
import ManageProduct from "./screens/ManageProduct";
import NewProductPage from "./screens/NewProduct";
import Register from "./screens/Register";
import ShoppingCart from "./screens/ShoppingCart";

function ECommerce() {
  return (
    <BrowserRouter>
      <Navbar />
      <Route path="/" exact={true} component={App} />
      <Route path="/new" exact={true} component={NewProductPage} />
      <Route path="/cart" exact={true} component={ShoppingCart} />
      <Route path="/login" exact={true} component={Login} />
      <Route path="/register" exact={true} component={Register} />
      <Route path="/forgot" exact={true} component={ForgotPassword} />
      <Route path="/manage" exact={true} component={ManageProduct} />
    </BrowserRouter>
  );
}

export default ECommerce;
