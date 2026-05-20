import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

const UpdateProduct = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [product, setProduct] = useState({});
  const [image, setImage] = useState(null);
  const [updateProduct, setUpdateProduct] = useState({
    name: "", description: "", brand: "", price: "", category: "", stockQuantity: "", productAvailable: false
  });

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/product/${id}`);
        setProduct(response.data);
        setUpdateProduct(response.data);
        if (response.data.imageName) {
          const imgRes = await axios.get(`http://localhost:8080/api/product/${id}/image`, { responseType: "blob" });
          setImage(new File([imgRes.data], response.data.imageName, { type: imgRes.data.type }));
        }
      } catch (error) { console.error(error); }
    };
    fetchProduct();
  }, [id]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append("imageFile", image);
    formData.append("product", new Blob([JSON.stringify(updateProduct)], { type: "application/json" }));

    axios.put(`http://localhost:8080/api/product/${id}`, formData, { headers: { "Content-Type": "multipart/form-data" } })
        .then(() => { alert("Updated successfully!"); navigate(`/product/${id}`); })
        .catch(() => { alert("Failed to update product."); });
  };

  return (
      <div className="update-product-container" style={{ padding: "3rem" }}>
        <form onSubmit={handleSubmit} className="row g-3">
          <div className="col-md-6">
            <label className="form-label">Name</label>
            <input type="text" className="form-control" value={updateProduct.name || ""} name="name" onChange={(e) => setUpdateProduct({...updateProduct, name: e.target.value})} />
          </div>
          <div className="col-md-6">
            <label className="form-label">Brand</label>
            <input type="text" className="form-control" value={updateProduct.brand || ""} name="brand" onChange={(e) => setUpdateProduct({...updateProduct, brand: e.target.value})} />
          </div>
          <div className="col-12">
            <label className="form-label">Description</label>
            <input type="text" className="form-control" value={updateProduct.description || ""} name="description" onChange={(e) => setUpdateProduct({...updateProduct, description: e.target.value})} />
          </div>
          <div className="col-md-4">
            <label className="form-label">Price</label>
            <input type="number" className="form-control" value={updateProduct.price || ""} name="price" onChange={(e) => setUpdateProduct({...updateProduct, price: e.target.value})} />
          </div>
          <div className="col-md-4">
            <label className="form-label">Stock Quantity</label>
            <input type="number" className="form-control" value={updateProduct.stockQuantity || ""} name="stockQuantity" onChange={(e) => setUpdateProduct({...updateProduct, stockQuantity: e.target.value})} />
          </div>
          <div className="col-md-4">
            <label className="form-label">Image</label>
            <input type="file" className="form-control" onChange={(e) => setImage(e.target.files[0])} />
          </div>
          <div className="col-12">
            <input type="checkbox" checked={updateProduct.productAvailable || false} onChange={(e) => setUpdateProduct({...updateProduct, productAvailable: e.target.checked})} /> Product Available
          </div>
          <button type="submit" className="btn btn-primary">Submit Update</button>
        </form>
      </div>
  );
};
export default UpdateProduct;
