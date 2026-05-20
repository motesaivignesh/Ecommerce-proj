import { useNavigate, useParams } from "react-router-dom";
import { useContext, useEffect } from "react";
import { useState } from "react";
import AppContext from "../Context/Context";
import axios from "../axios";

const Product = () => {
  const { id } = useParams();
  const { addToCart, removeFromCart, refreshData } =
      useContext(AppContext);
  const [product, setProduct] = useState(null);
  const [imageUrl, setImageUrl] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const fetchProductAndImage = async () => {
      try {
        const response = await axios.get(
            `http://localhost:8080/api/product/${id}`
        );
        setProduct(response.data);
        if (response.data.imageName) {
          const responseImage = await axios.get(
              `http://localhost:8080/api/product/${id}/image`,
              { responseType: "blob" }
          );
          setImageUrl(URL.createObjectURL(responseImage.data));
        }
      } catch (error) {
        console.error("Error fetching product data details:", error);
      }
    };

    fetchProductAndImage();
  }, [id]);

  const deleteProduct = async () => {
    try {
      await axios.delete(`http://localhost:8080/api/product/${id}`);
      removeFromCart(id);
      console.log("Product deleted successfully");
      alert("Product deleted successfully");
      refreshData();
      navigate("/");
    } catch (error) {
      console.error("Error deleting product data entry:", error);
    }
  };

  const handleEditClick = () => {
    navigate(`/product/update/${id}`);
  };

  const handlAddToCart = () => {
    addToCart(product);
    alert("Product added to cart");
  };

  if (!product) {
    return (
        <h2 className="text-center" style={{ padding: "10rem" }}>
          Loading...
        </h2>
    );
  }

  return (
      <>
        {/* Restored the exact class names containers, product-description, etc. */}
        <div className="containers">
          <img
              className="left-column-img"
              src={imageUrl}
              alt={product.imageName}
          />

          <div className="right-column">
            <div className="product-description">
              <span>{product.category}</span>
              <h1>{product.name}</h1>
              <h5>{product.brand}</h5>
              {/* Swapped back from 'desc' to 'description' to perfectly match model updates */}
              <p>{product.description}</p>
            </div>

            <div className="product-price">
              {/* Added a clean space between the dollar sign and price layout */}
              <span>{"$ " + product.price}</span>
              <button
                  className={`cart-btn ${
                      !product.productAvailable ? "disabled-btn" : ""
                  }`}
                  onClick={handlAddToCart}
                  disabled={!product.productAvailable}
              >
                {product.productAvailable ? "Add to cart" : "Out of Stock"}
              </button>
              <h6>
                Stock Available :{" "}
                <i style={{ color: "green", fontWeight: "bold" }}>
                  {product.stockQuantity}
                </i>
              </h6>

              {/* RESTORED: The release date field layout section */}
              <div className="release-date">
                <h6>Product listed on:</h6>
                <i> {product.releaseDate ? new Date(product.releaseDate).toLocaleDateString() : "N/A"}</i>
              </div>
            </div>
            <div className="update-button ">
              <button
                  className="btn btn-primary"
                  type="button"
                  onClick={handleEditClick}
              >
                Update
              </button>
              <button
                  className="btn btn-primary"
                  type="button"
                  onClick={deleteProduct}
              >
                Delete
              </button>
            </div>
          </div>
        </div>
      </>
  );
};

export default Product;
