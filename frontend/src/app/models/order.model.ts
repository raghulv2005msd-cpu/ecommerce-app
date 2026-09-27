import { Product } from './product.model';

export interface OrderItem {
  id: number;
  product: Product;
  quantity: number;
  priceAtPurchase: number;
}

export type OrderStatus = 'PENDING' | 'PAID' | 'SHIPPED' | 'DELIVERED' | 'CANCELLED';

export interface Order {
  id: number;
  items: OrderItem[];
  totalAmount: number;
  status: OrderStatus;
  shippingAddress: string;
  createdAt: string;
}

export interface OrderRequest {
  shippingAddress: string;
}
