import axios from "../axios";
import { useState, useEffect, createContext } from "react";

const AppContext = createContext({
  data: [],
  isError: "",
  cart: [],
  addToCart: (product) => {},
  removeFromCart: (productId) => {},
  refreshData: () => {}
});

export const AppProvider = ({ children }) => {
  const [data, setData] = useState([]);
  const [isError, setIsError] = useState("");
  const [cart, setCart] = useState(JSON.parse(localStorage.getItem('cart')) || []);

  const addToCart = (product) => {
    const existingProductIndex = cart.findIndex((item) => item.id === product.id);
    if (existingProductIndex !== -1) {
      const updatedCart = cart.map((item, index) =>
          index === existingProductIndex ? { ...item, quantity: item.quantity + 1 } : item
      );
      setCart(updatedCart);
    } else {
      setCart([...cart, { ...product, quantity: 1 }]);
    }
  };

  const removeFromCart = (productId) => {
    setCart(cart.filter((item) => item.id !== productId));
  };

  const refreshData = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/products");
      setData(response.data);
    } catch (error) {
      setIsError(error.message);
    }
  };

  useEffect(() => { refreshData(); }, []);
  useEffect(() => { localStorage.setItem('cart', JSON.stringify(cart)); }, [cart]);

  return (
      <AppContext.Provider value={{ data, isError, cart, addToCart, removeFromCart, refreshData }}>
        {children}
      </AppContext.Provider>
  );
};
export default AppContext;
